package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.space.speedysensors.R
import com.space.speedysensors.ui.viewmodels.HumiditySensorViewModel
import kotlinx.android.synthetic.main.fragment_humidity_sensor.*

class HumiditySensorFragment : Fragment(R.layout.fragment_humidity_sensor) {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(HumiditySensorViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.humidity.observe(this, Observer { humidity ->
            humidity?.let {
                var value = it.toString()
                textViewHumidityValue.text = "${value}%"
            }
        })
    }

}
