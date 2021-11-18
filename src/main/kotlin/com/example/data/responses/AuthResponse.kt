package com.example.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String = "",
    val successful: Boolean = false,
    val wrongPassword: Boolean = false,
    val userDoesntExist: Boolean = false
)
