package com.example.data.final_models

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutDay(
    val exercicesList: List<Exercice>,
    val waterQty: Long,
    val isDone :Boolean
)
