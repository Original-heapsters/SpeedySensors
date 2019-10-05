package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.space.speedysensors.R
import com.space.speedysensors.ui.viewmodels.PressureSensorViewModel
import kotlinx.android.synthetic.main.fragment_pressure_sensor.*

class PressureSensorFragment : Fragment(R.layout.fragment_pressure_sensor) {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(PressureSensorViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pressure.observe(this, Observer { pressure ->
            pressure?.let {
                var value = it.toString()
                textViewPressureValue.text = "${value}hPa"
            }
        })
    }

}
