package com.space.speedysensors.models

import com.squareup.moshi.Json

data class Subscribe(
        @Json(name = "id") val id: String,
        @Json(name = "username") val username: String,
        @Json(name = "group") val group: String,
        @Json(name = "manager") val manager: Boolean,
        @Json(name = "timestamp") val timestamp: String
)