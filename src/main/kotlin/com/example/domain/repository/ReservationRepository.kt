package com.example.domain.repository

import com.example.data.models.Reservation
import com.mongodb.client.result.InsertOneResult
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ReservationRepository(
    db: CoroutineDatabase
) {
    val reservations = db.getCollection<Reservation>()

    suspend fun makeReservation(
        userId: String,
        businessId: String,
        businessName: String
    ): InsertOneResult {
        return reservations.insertOne(
            Reservation(
                userId = userId,
                businessId = businessId,
                timestamp = System.currentTimeMillis(),
                businessName = businessName
            )
        )
    }

    suspend fun getReservationByUserId(
        userId: String
    ): Reservation? {
        return reservations.findOne(Reservation::userId eq userId)
    }

    suspend fun getReservationsByBusinessId(
        businessId: String
    ) : List<Reservation> {
        return reservations.find(Reservation::businessId eq businessId).toList()
    }
}