package com.space.speedysensors.ui.viewmodels

import android.app.Application
import android.hardware.Sensor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nkzawa.emitter.Emitter
import com.space.speedysensors.models.SensorPayload
import com.space.speedysensors.services.SocketService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val _connectedUsers = MutableLiveData<ArrayList<SensorPayload>>()
    val connectedUsers: LiveData<ArrayList<SensorPayload>>
        get() = _connectedUsers

    private val onUpdate = Emitter.Listener { args ->
        val payload = args[0] as String
        val json = Json(JsonConfiguration.Stable)
        val data = json.parse(SensorPayload.serializer(), payload)

        val currentUsers = _connectedUsers.value ?: arrayListOf()
        currentUsers.firstOrNull { it.id == data.id }?.apply {
            accelerometer = data.accelerometer
        } ?: run {
            currentUsers.add(data)
        }

        _connectedUsers.postValue(currentUsers)
    }

    init {
        SocketService.instance.addOnUpdateListener(onUpdate)
    }

    override fun onCleared() {
        super.onCleared()
    }

}
