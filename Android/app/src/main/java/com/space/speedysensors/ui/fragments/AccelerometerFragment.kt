package com.space.speedysensors.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket

import com.space.speedysensors.R
import com.space.speedysensors.ui.viewmodels.AccelerometerViewModel
import kotlinx.android.synthetic.main.fragment_accelerometer.*

class AccelerometerFragment : Fragment(R.layout.fragment_accelerometer) {

    private var mSocket: Socket? = null
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(AccelerometerViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.mSocket = IO.socket("http://10.64.6.95     :5000/")

        this.mSocket?.connect()
        if (mSocket?.connected() == true){
            Toast.makeText(this.context, "Connected!!", Toast.LENGTH_SHORT).show();
        }
        this.mSocket?.emit("echo", "test")
        viewModel.x.observe(this, Observer { x ->
            x?.let {
                var value = it.toString()
                val isNegative = value.first() == '-'
                if (isNegative) {
                    value = value.removePrefix("-")
                }
                textViewNegativeX.visibility = if (isNegative) View.VISIBLE else View.INVISIBLE
                textViewXValue.text = value
            }
        })

        viewModel.y.observe(this, Observer { y ->
            y?.let {
                var value = it.toString()
                val isNegative = value.first() == '-'
                if (isNegative) {
                    value = value.removePrefix("-")
                }
                textViewNegativeY.visibility = if (isNegative) View.VISIBLE else View.INVISIBLE
                textViewYValue.text = value
            }
        })

        viewModel.z.observe(this, Observer { z ->
            z?.let {
                var value = it.toString()
                val isNegative = value.first() == '-'
                if (isNegative) {
                    value = value.removePrefix("-")
                }
                textViewNegativeZ.visibility = if (isNegative) View.VISIBLE else View.INVISIBLE
                textViewZValue.text = value
            }
            this.mSocket?.emit("socketboi", "{\"id\":\"test\",\"accelerometer\":["+viewModel.x.value+","+viewModel.y.value+","+viewModel.z.value+"]}")
        })
    }

}
