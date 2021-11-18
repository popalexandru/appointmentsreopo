package com.example.data.responses

data class AuthResponse(
    val token: String = "",
    val successful: Boolean = false,
    val wrongPassword: Boolean = false,
    val userDoesntExist: Boolean = false
)
