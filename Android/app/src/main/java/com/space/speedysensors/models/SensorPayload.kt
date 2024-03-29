package com.space.speedysensors.models

import kotlinx.serialization.*

@Serializable
data class SensorPayload(
        val id: String,
        val role: String,
        val timestamp: Long,
        var accelerometer: FloatArray
)