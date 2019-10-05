package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.space.speedysensors.ui.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.illuminance.observe(this, Observer { illuminance ->
//            illuminance?.let {
//                var value = it.toString()
//                textViewIlluminanceValue.text = "${value}lx"
//            }
//        })
    }

}
