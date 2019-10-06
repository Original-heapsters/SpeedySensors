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
    var socketAddress: String = ""

    fun connect(username: String, role: String) {
        this.username = username
        this.role = role
        this.socket = IO.socket(socketAddress).apply {
            connect()
        }
    }

    fun sendData(data: FloatArray) {
        val payload = SensorPayload(
                id = username,
                role = role,
                timestamp = System.currentTimeMillis() / 1000L,
                accelerometer = data
        )
        val json = Json(JsonConfiguration.Stable)
        val jsonData = json.stringify(SensorPayload.serializer(), payload)
        socket?.emit("socketboi", jsonData)
    }

    fun addOnUpdateListener(listener: Emitter.Listener) {
        socket?.on("update", listener)
    }

    fun addOnAnomalyListener(listener: Emitter.Listener) {
        socket?.on("anomaly", listener)
    }

    fun disconnect() {

    }

}