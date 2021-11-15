package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutFinishRequest(
    val workoutId: String
)
