package com.example.domain.service

import com.example.data.models.Workout
import com.example.domain.repository.WorkoutRepository
import org.litote.kmongo.setTo

class WorkoutService(
    private val repository: WorkoutRepository
) {
    suspend fun getWorkout(
        userId: String,
        timestamp: Long): Workout?{
        return repository.getWorkout(userId, timestamp
        )
    }

    suspend fun addEmptyWorkout(
        userId: String,
        timestamp: Long
    ){
        repository.createEmptyWorkout(userId, timestamp)
    }

    suspend fun finishWorkout(
        workoutId: String
    ){
        repository.finishWorkout(workoutId)
    }
}