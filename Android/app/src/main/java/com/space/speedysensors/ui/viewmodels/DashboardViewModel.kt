package com.space.speedysensors.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nkzawa.emitter.Emitter
import com.space.speedysensors.models.Anomaly
import com.space.speedysensors.models.SensorPayload
import com.space.speedysensors.services.PushNotifcationService
import com.space.speedysensors.services.SocketService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.util.concurrent.TimeUnit

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

//    private val compositeDisposable = CompositeDisposable()

    private val _connectedUsers = MutableLiveData<ArrayList<SensorPayload>>()
    val connectedUsers: LiveData<ArrayList<SensorPayload>>
        get() = _connectedUsers

    private val _anomalies = MutableLiveData<ArrayList<Anomaly>>()
    val anomalies: LiveData<ArrayList<Anomaly>>
        get() = _anomalies

//    private val updateObservable =
//            Observable
//                    .create<ArrayList<SensorPayload>> { emitter ->
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

//                            emitter.onNext(currentUsers)
    }

//                        SocketService.instance.addOnUpdateListener(onUpdate)
//                    }
//                    .debounce(20, TimeUnit.MILLISECONDS)
//                    .observeOn(Schedulers.io())
//                    .subscribeOn(Schedulers.io())



    private val onAnomaly = Emitter.Listener { args ->
        val payload = args[0] as String
        val json = Json(JsonConfiguration.Stable)
        val data = json.parse(Anomaly.serializer(), payload)

        val currentAnomalies = _anomalies.value ?: arrayListOf()
        if (!currentAnomalies.contains(data)) {
            currentAnomalies.add(data)
        }

        PushNotifcationService.instance.show(application.applicationContext, "Emergency!!", "Anomaly detected from ${data.id}!")
        _anomalies.postValue(currentAnomalies)
    }

    init {
        SocketService.instance.addOnUpdateListener(onUpdate)
        SocketService.instance.addOnAnomalyListener(onAnomaly)

//        updateObservable
//                .subscribeBy(
//                        onError = {  },
//                        onNext = { _connectedUsers.postValue(it) },
//                        onComplete = {  }
//                )
//                .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun resolveAnomaly(id: String) {
        val currentAnomalies = _anomalies.value ?: arrayListOf()
        currentAnomalies.removeAll {
            it.id == id
        }
        _anomalies.postValue(currentAnomalies)
    }

}
