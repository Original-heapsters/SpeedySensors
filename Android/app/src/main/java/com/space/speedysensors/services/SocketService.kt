package com.space.speedysensors.services

import android.util.Log
import okhttp3.*
import okio.ByteString

class SocketService {

    companion object {
        private val TAG: String = SocketService::class.java.simpleName

        val instance = SocketService()
    }

    private val client = OkHttpClient()
    private var socket: WebSocket? = null

    private val listener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            socket = webSocket
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

    fun connect() {
        val request = Request.Builder()
                .url("http://10.64.6.95:5000")
                .build()
        client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
    }

    fun sendMessage(message: String) {
        socket?.send(message)
    }

    fun disconnect() {

    }

}