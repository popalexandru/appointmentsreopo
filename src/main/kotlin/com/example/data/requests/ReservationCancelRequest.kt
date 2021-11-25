package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class ReservationCancelRequest(
    val reservationId: String
)
