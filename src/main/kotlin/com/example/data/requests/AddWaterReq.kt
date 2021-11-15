package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddWaterReq(
    val userId: String,
    val waterQty: Long
)
