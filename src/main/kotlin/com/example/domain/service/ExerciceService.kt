package com.example.domain.service

import com.example.data.models.Exercice
import com.example.domain.repository.ExerciceRepository

class ExerciceService(
    val repository: ExerciceRepository
) {

    suspend fun getExercicesByWorkout(
        workoutId: String
    ): List<Exercice>{
        println("searching exercices for workout id: $workoutId")
        return repository.getExercicesByWorkoutId(workoutId)
    }

    suspend fun addExerciceToWorkout(
        workoutId: String,
        exampleId: String
    ){
        repository.addExerciceToWorkout(exampleId, workoutId)
    }
}