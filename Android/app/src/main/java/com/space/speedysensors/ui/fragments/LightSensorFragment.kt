package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.space.speedysensors.R
import com.space.speedysensors.ui.viewmodels.LightSensorViewModel
import kotlinx.android.synthetic.main.fragment_light_sensor.*

class LightSensorFragment : Fragment(R.layout.fragment_light_sensor) {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LightSensorViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.illuminance.observe(this, Observer { illuminance ->
            illuminance?.let {
                var value = it.toString()
                textViewIlluminanceValue.text = "${value}lx"
            }
        })
    }

}
