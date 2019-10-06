package com.space.speedysensors.models

import kotlinx.serialization.*

@Serializable
class SensorPayload(
        val id: String,
        var accelerometer: FloatArray
)