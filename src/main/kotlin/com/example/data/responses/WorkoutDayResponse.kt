package com.example.data.responses

import com.example.data.final_models.Exercice

data class WorkoutDayResponse(
    val exercices: List<Exercice>,
    val waterQty: Long,
    val isDone: Boolean
)
