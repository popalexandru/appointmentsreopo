package com.example.domain.repository

import com.example.data.models.Exercice
import com.example.data.models.Workout
import com.example.util.Utility
import com.mongodb.client.model.UpdateOptions
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.set
import org.litote.kmongo.setTo

class WorkoutRepository(
    db: CoroutineDatabase
) {
    val workout = db.getCollection<Workout>()

    suspend fun getWorkout(
        userId: String,
        timestamp: Long
    ) : Workout? {
        val date = Utility.formatTimestamp(timestamp)

        return workout.findOne(
            and(
                Workout::userId eq userId,
                Workout::date eq date
            )
        )
    }

    suspend fun createEmptyWorkout(
        userId: String,
        timestamp: Long
    ) = workout.insertOne(
        Workout(
            userId = userId,
            date = Utility.formatTimestamp(timestamp),
            isDone = false
        )
    )

    suspend fun finishWorkout(
        workoutId: String
    ){
        workout.updateOne(
            Workout::workoutId eq workoutId,
            set(Workout::isDone setTo true)
        )
    }
}