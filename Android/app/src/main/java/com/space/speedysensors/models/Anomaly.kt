package com.space.speedysensors.models

import kotlinx.serialization.Serializable

@Serializable
data class Anomaly(
    val anomaly: String,
    val id: String,
    val magnitude: Double
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Anomaly -> other.id == this.id
            else -> false
        }
    }
}