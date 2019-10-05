package com.space.speedysensors.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.space.speedysensors.R
import com.space.speedysensors.ui.viewmodels.TemperatureViewModel
import kotlinx.android.synthetic.main.fragment_temperature.*

class TemperatureFragment : Fragment(R.layout.fragment_temperature) {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(TemperatureViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.temperature.observe(this, Observer { x ->
            x?.let {
                var value = it.toString()
                val isNegative = value.first() == '-'
                if (isNegative) {
                    value = value.removePrefix("-")
                }
                textViewTemperatureValue.text = "$value°C"
            }
        })
    }

}
