package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class ReservationRequest(
    val restaurantId: String
)
