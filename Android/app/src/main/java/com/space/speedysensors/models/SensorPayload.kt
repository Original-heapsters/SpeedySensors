package com.space.speedysensors.models

import kotlinx.serialization.Serializable

@Serializable
data class SensorPayload(
        val accelerometer: ArrayList<Float>
)