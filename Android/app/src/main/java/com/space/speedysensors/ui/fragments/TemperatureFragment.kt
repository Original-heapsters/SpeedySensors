package com.space.speedysensors.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.space.speedysensors.R

class TemperatureFragment : Fragment() {

    companion object {
        fun newInstance() = TemperatureFragment()
    }

    private lateinit var viewModel: TemperatureViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.temperature_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TemperatureViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
