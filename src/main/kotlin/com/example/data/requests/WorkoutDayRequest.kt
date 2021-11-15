package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutDayRequest(
    val userId: String,
    val timestamp: Long
)
