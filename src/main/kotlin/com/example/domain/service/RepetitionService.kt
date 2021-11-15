package com.example.domain.service

import com.example.data.models.Repetition
import com.example.domain.repository.RepetitionRepository

class RepetitionService(
    private val repository: RepetitionRepository
) {

    suspend fun getRepetitions(
        workoutId: String,
        exerciceId: String
    ) : List<Repetition>{
        return repository.getRepetitions(
            exerciceId, workoutId
        )
    }

    suspend fun addRepetition(
        count: Int,
        weight: Int,
        workoutId: String,
        exerciceId: String
    ){
        repository.addRepetition(
            count, weight, workoutId, exerciceId
        )
    }
}