package com.space.speedysensors.ui.viewmodels

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PressureSensorViewModel(application: Application) : AndroidViewModel(application) {

    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null

    private val listener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            event?.apply {
                _pressure.postValue(event.values.firstOrNull() ?: 0f)
            }
        }
    }

    private val _pressure = MutableLiveData<Float>()
    val pressure: LiveData<Float>
        get() = _pressure

    init {
        startSensor()
    }

    override fun onCleared() {
        stopSensor()
        super.onCleared()
    }

    private fun startSensor() {
        val application = getApplication<Application>()
        sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PRESSURE)
        sensorManager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun stopSensor() {
        sensorManager?.unregisterListener(listener)
    }

}
