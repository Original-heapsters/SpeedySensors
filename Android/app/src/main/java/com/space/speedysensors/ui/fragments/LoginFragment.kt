package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.space.speedysensors.R
import com.space.speedysensors.services.SocketService
import com.space.speedysensors.ui.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text?.toString() ?: ""
            val sockerAddr= editTextIP.text?.toString() ?: ""
            val role = when(radioGroup.checkedRadioButtonId) {
                R.id.radioButtonFirefighter -> "Firefighter"
                R.id.radioButtonPoliceOfficer -> "Police Officer"
                R.id.radioButtonEMT -> "EMT"
                R.id.radioButtonManager -> "Manager"
                else -> "badboi"
            }

            if (username.isNotEmpty() && sockerAddr.isNotEmpty() && role != "badboi") {
                SocketService.instance.socketAddres = "http://${sockerAddr}:5000"
                SocketService.instance.connect(username, role)
                when (role) {
                    "Manager" -> { findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment) }
                    else -> { findNavController().navigate(R.id.action_loginFragment_to_mainFragment) }
                }

            }

        }
    }

}
