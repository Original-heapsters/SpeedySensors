package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke

import com.space.speedysensors.R
import com.space.speedysensors.models.SensorPayload
import com.space.speedysensors.ui.adapters.PayloadAdapter
import com.space.speedysensors.ui.adapters.PayloadViewHolder
import com.space.speedysensors.ui.viewmodels.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val payloadAdapter = PayloadAdapter()
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
            mSeries.appendData(DataPoint(mSeries.highestValueX + Math.random() * 2, users[0].accelerometer.average()),true,100000)
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

    private fun setupChart(): Cartesian? {
        val cartesian = AnyChart.line()

        cartesian.animation(true)

        cartesian.padding(10.0, 20.0, 5.0, 20.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true)
            // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title("Trend of Sales of the Most Popular Products of ACME Corp.")

        cartesian.yAxis(0).title("Number of Bottles Sold (thousands)")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)
        val seriesData = ArrayList<ValueDataEntry>()
        seriesData.add(ValueDataEntry("1986", 3.6))
        seriesData.add(ValueDataEntry("1987", 7.1))
        seriesData.add(ValueDataEntry("1988", 8.5))
        seriesData.add(ValueDataEntry("1989", 9.2))
        seriesData.add(ValueDataEntry("1990", 10.1))
        seriesData.add(ValueDataEntry("1991", 11.6))
        seriesData.add(ValueDataEntry("1992", 16.4))
        seriesData.add(ValueDataEntry("1993", 18.0))
        seriesData.add(ValueDataEntry("1994", 13.2))
        seriesData.add(ValueDataEntry("1995", 12.0))
        seriesData.add(ValueDataEntry("1996", 3.2))
        seriesData.add(ValueDataEntry("1997", 4.1))
        seriesData.add(ValueDataEntry("1998", 6.3))
        seriesData.add(ValueDataEntry("1999", 9.4))
        seriesData.add(ValueDataEntry("2000", 11.5))
        seriesData.add(ValueDataEntry("2001", 13.5))
        seriesData.add(ValueDataEntry("2002", 14.8))
        seriesData.add(ValueDataEntry("2003", 16.6))
        seriesData.add(ValueDataEntry("2004", 18.1))
        seriesData.add(ValueDataEntry("2005", 17.0))
        seriesData.add(ValueDataEntry("2006", 16.6))
        seriesData.add(ValueDataEntry("2007", 14.1))
        seriesData.add(ValueDataEntry("2008", 15.7))
        seriesData.add(ValueDataEntry("2009", 12.0))


        val series1 = cartesian.line(seriesData.toList())
        series1.name("Brandy")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER.jsBase)
            .offsetX(5.0)
            .offsetY(5.0)


        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        return cartesian
    }

}
