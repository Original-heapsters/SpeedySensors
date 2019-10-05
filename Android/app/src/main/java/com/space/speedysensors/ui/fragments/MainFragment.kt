package com.space.speedysensors.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.space.speedysensors.R
import com.space.speedysensors.ui.viewmodels.MainViewModel
import okhttp3.*
import okio.ByteString

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        private val TAG: String = MainFragment::class.java.simpleName
    }

    private val client = OkHttpClient()

    private val listener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
//            webSocket.send("FUCKBOI")
            Log.i(TAG, "onOpen")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Log.i(TAG, "onMessage: $text")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            Log.i(TAG, "onMessage")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            Log.i(TAG, "onClosing: $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.i(TAG, "onFailure: ${t.localizedMessage}")
        }
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = Request.Builder()
//                .url("wss://ws-feed.gdax.com")
                .url("http://10.64.6.95:5000")
                .build()
        client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
    }

}
