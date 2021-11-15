package com.example.domain.repository

import com.example.data.models.Repetition
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class RepetitionRepository(
    db: CoroutineDatabase
) {
    val repetitions = db.getCollection<Repetition>()

    suspend fun getRepetitions(
        exerciceId: String,
        workoutId: String
    ): List<Repetition> {
        return repetitions.find(
            and(
                Repetition::workoutId eq workoutId,
                Repetition::exerciceId eq exerciceId
            )
        ).toList()
    }

    suspend fun addRepetition(
        count: Int,
        weight: Int,
        workoutId: String,
        exerciceId: String
    ) {
        repetitions.insertOne(
            Repetition(
                workoutId,
                exerciceId,
                count,
                weight
            )
        )
    }
}