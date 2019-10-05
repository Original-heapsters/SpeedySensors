package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.space.speedysensors.R
import com.space.speedysensors.ui.viewmodels.MainViewModel
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONObject
import com.github.nkzawa.emitter.Emitter




class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        private val TAG: String = MainFragment::class.java.simpleName
    }

    private var mSocket:Socket? = null



    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }


    val onEcho = Emitter.Listener { args ->
        val data = args[0] as String
        //here the data is in JSON Format
        requireActivity().runOnUiThread {
            Toast.makeText(requireActivity(), data, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.mSocket = IO.socket("http://10.64.6.95:5000/")
        this.mSocket?.on("echo", onEcho)
        this.mSocket?.connect()
        if (mSocket?.connected() == true){
            Toast.makeText(this.context, "Connected!!", Toast.LENGTH_SHORT).show();
        }
        this.mSocket?.emit("echo", "test")

    }

}
