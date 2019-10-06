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
import com.space.speedysensors.services.SocketService

class AccelerometerViewModel(application: Application) : AndroidViewModel(application) {

    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null

    private val listener = object : SensorEventListener {
        private var gravity: ArrayList<Float> = arrayListOf(0f, 0f, 0f, 0f, 0f)
        private var linearAcceleration: ArrayList<Float> = arrayListOf(0f, 0f, 0f)

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            event?.apply {
                val alpha = 0.8f

                // Isolate the force of gravity with the low-pass filter.
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

                // Remove the gravity contribution with the high-pass filter.
                linearAcceleration[0] = event.values[0] - gravity[0]
                linearAcceleration[1] = event.values[1] - gravity[1]
                linearAcceleration[2] = event.values[2] - gravity[2]

                _x.postValue(linearAcceleration[0])
                _y.postValue(linearAcceleration[1])
                _z.postValue(linearAcceleration[2])

                SocketService.instance.sendData(event.values)
            }
        }
    }

    private val _x = MutableLiveData<Float>()
    val x: LiveData<Float>
        get() = _x

    private val _y = MutableLiveData<Float>()
    val y: LiveData<Float>
        get() = _y

    private val _z = MutableLiveData<Float>()
    val z: LiveData<Float>
        get() = _z

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
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
    }

    private fun stopSensor() {
        sensorManager?.unregisterListener(listener)
    }
}
