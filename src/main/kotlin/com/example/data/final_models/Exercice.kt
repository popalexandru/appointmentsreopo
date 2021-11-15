package com.example.data.final_models

import kotlinx.serialization.Serializable

@Serializable
data class Exercice(
    val exerciceId: String,
    val exerciceName: String,
    val exerciceDescription: String,
    val exerciceImg: String,
    val reps: List<Rep>
)
