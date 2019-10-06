package com.space.speedysensors.services

import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.space.speedysensors.models.SensorPayload
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class SocketService {

    companion object {
        private val TAG: String = SocketService::class.java.simpleName

        val instance = SocketService()
    }

    private var socket: Socket? = null
    private var username: String = ""
    private var role: String = ""

    fun connect(username: String, role: String) {
        this.username = username
        this.role = role
        this.socket = IO.socket("http://10.64.6.95:5000/").apply {
            connect()
        }
    }

    fun sendData(data: SensorPayload) {
        val json = Json(JsonConfiguration.Stable)
        val jsonData = json.stringify(SensorPayload.serializer(), data)
        socket?.emit("socketboi", jsonData)
    }

    fun addOnUpdateListener(listener: Emitter.Listener) {
        socket?.on("update", listener)
    }

    fun disconnect() {

    }

}