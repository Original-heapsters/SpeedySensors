package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.space.speedysensors.R
import com.space.speedysensors.models.SensorPayload
import com.space.speedysensors.ui.adapters.PayloadAdapter
import com.space.speedysensors.ui.adapters.PayloadViewHolder
import com.space.speedysensors.ui.viewmodels.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

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
        })

        viewModel.anomalies.observe(this, Observer { anomalies ->
            anomalies?.let {
                payloadAdapter.anomalyDetected(anomalies)
            }
        })
    }

}
