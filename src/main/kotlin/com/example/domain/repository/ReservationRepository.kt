package com.example.domain.repository

import com.example.data.models.Reservation
import org.litote.kmongo.coroutine.CoroutineDatabase

class ReservationRepository(
    db: CoroutineDatabase
) {
    val reservations = db.getCollection<Reservation>()

    suspend fun makeReservation(
        userId: String
    ){
        reservations.insertOne(
            Reservation(
                userId = userId,
                timestamp = System.currentTimeMillis()
            )
        )
    }
}