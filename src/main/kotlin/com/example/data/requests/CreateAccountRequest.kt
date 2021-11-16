package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    val email: String,
    val password: String
)
