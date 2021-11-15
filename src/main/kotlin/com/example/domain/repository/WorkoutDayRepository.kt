package com.example.domain.repository

import com.example.data.models.*
import org.litote.kmongo.coroutine.CoroutineDatabase

class WorkoutDayRepository(
    db: CoroutineDatabase
) {
    val workout = db.getCollection<Workout>()

}