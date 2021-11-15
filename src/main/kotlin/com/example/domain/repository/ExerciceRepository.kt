package com.example.domain.repository

import com.example.data.models.Exercice
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ExerciceRepository(
    db: CoroutineDatabase
) {
    val exercice = db.getCollection<Exercice>()

    suspend fun getExercicesByWorkoutId(
        workoutId: String
    ) : List<Exercice> {
        exercice.insertOne(
            Exercice(
                "02",
                "02"
            )
        )
        return exercice.find(Exercice::workoutId eq workoutId)
            .toList()
    }

    suspend fun addExerciceToWorkout(
        exampleId: String,
        workoutId: String
    ) {
        exercice.insertOne(
            Exercice(
                workoutId = workoutId,
                exerciceId = exampleId
            )
        )
    }
}