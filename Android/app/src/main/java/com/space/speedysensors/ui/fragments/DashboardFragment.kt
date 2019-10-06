package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.space.speedysensors.R
import com.space.speedysensors.ui.viewmodels.DashboardViewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.connectedUsers.observe(this, Observer { users ->
            users?.let {

            }
        })
    }

}
