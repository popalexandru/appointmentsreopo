package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)
