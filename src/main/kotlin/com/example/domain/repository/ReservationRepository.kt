package com.example.domain.repository

import com.example.data.models.Reservation
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import java.util.concurrent.TimeUnit

class ReservationRepository(
    db: CoroutineDatabase
) {
    val reservations = db.getCollection<Reservation>()

    suspend fun makeReservation(
        userId: String,
        businessId: String,
        businessName: String,
        timestamp: Long,
        serviceId: String
    ): InsertOneResult {
        return reservations.insertOne(
            Reservation(
                userId = userId,
                businessId = businessId,
                timestampDone = System.currentTimeMillis(),
                businessName = businessName,
                timestampDue = timestamp,
                serviceId = serviceId
            )
        )
    }

    suspend fun cancelReservation(
        reservationId: String
    ): DeleteResult {
        return reservations.deleteOne(Reservation::reservationId eq reservationId)
    }

    suspend fun getReservationByUserId(
        userId: String
    ): Reservation? {
        return reservations.findOne(Reservation::userId eq userId)
    }

    suspend fun getReservationByUserAndBusiness(
        userId: String,
        businessId: String
    ): Reservation?{
        return reservations.findOne(
            and(
              Reservation::userId eq userId,
              Reservation::businessId eq businessId
            )
        )
    }

    suspend fun getReservationsByBusinessId(
        businessId: String
    ) : List<Reservation> {
        return reservations.find(Reservation::businessId eq businessId).toList()
    }

    suspend fun getReservationsByUserId(
        userId: String
    ): List<Reservation>{
        return reservations.find(Reservation::userId eq userId).toList()
    }
}