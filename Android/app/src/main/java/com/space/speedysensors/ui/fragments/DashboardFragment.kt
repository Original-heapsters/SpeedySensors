package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.space.speedysensors.R
import com.space.speedysensors.models.SensorPayload
import com.space.speedysensors.ui.adapters.PayloadAdapter
import com.space.speedysensors.ui.adapters.PayloadViewHolder
import com.space.speedysensors.ui.viewmodels.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val mSeries = LineGraphSeries<DataPoint>()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    private val payloadCellListener = object : PayloadViewHolder.PayloadViewHolderListener {
        override fun cellClicked(payload: SensorPayload) {
            viewModel.resolveAnomaly(payload.id)
        }
    }

    private val payloadAdapter = PayloadAdapter(payloadCellListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            itemAnimator = null
            adapter = payloadAdapter
        }

        viewModel.connectedUsers.observe(this, Observer { users ->
            users?.let {
                payloadAdapter.updatePayloads(users)
            }
            val diff = System.currentTimeMillis() - users[0].timestamp.toDouble()
            mSeries.appendData(DataPoint(diff, users[0].accelerometer.average()),true,100000)
        })

        view.graph.viewport.isXAxisBoundsManual = true
        view.graph.viewport.isYAxisBoundsManual = true
        view.graph.viewport.setMinX(0.0)
        view.graph.viewport.setMaxX(40.0)
        view.graph.viewport.setMinY(0.0)
        view.graph.viewport.setMaxY(10.0)

        view.graph.gridLabelRenderer.labelVerticalWidth = 100;
        mSeries.isDrawDataPoints = true
        mSeries.isDrawBackground = true
        view.graph.addSeries(mSeries)
        viewModel.anomalies.observe(this, Observer { anomalies ->
            anomalies?.let {
                payloadAdapter.anomalyDetected(anomalies)
            }
        })
    }
}
