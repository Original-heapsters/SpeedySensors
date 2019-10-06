package com.space.speedysensors.models

import kotlinx.serialization.*

@Serializable
data class SensorPayload(
        val id: String,
        var accelerometer: FloatArray
)