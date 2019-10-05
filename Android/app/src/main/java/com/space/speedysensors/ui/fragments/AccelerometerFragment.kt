package com.space.speedysensors.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.space.speedysensors.R
import kotlinx.android.synthetic.main.accelerometer_fragment.*

class AccelerometerFragment : Fragment(R.layout.accelerometer_fragment) {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(AccelerometerViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.x.observe(this, Observer { x ->
            x?.let {
                var value = it.toString()
                val isNegative = value.first() == '-'
                if (isNegative) {
                    value = value.removePrefix("-")
                }
                xNegativeView.visibility = if (isNegative) View.VISIBLE else View.INVISIBLE
                xValueTextView.text = value
            }
        })

        viewModel.y.observe(this, Observer { y ->
            y?.let {
                var value = it.toString()
                val isNegative = value.first() == '-'
                if (isNegative) {
                    value = value.removePrefix("-")
                }
                yNegativeView.visibility = if (isNegative) View.VISIBLE else View.INVISIBLE
                yValueTextView.text = value
            }
        })

        viewModel.z.observe(this, Observer { z ->
            z?.let {
                var value = it.toString()
                val isNegative = value.first() == '-'
                if (isNegative) {
                    value = value.removePrefix("-")
                }
                zNegativeView.visibility = if (isNegative) View.VISIBLE else View.INVISIBLE
                zValueTextView.text = value
            }
        })
    }

}
