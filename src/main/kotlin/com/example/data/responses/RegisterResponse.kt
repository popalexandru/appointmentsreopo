package com.example.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val successful: Boolean = false,
    val wrongPassword: Boolean = false,
    val userAlreadyExists: Boolean = false,
    val fieldsAreBlank : Boolean = false
)
