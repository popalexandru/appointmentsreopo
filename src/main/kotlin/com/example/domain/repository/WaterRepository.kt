package com.example.domain.repository

import com.example.data.models.Water
import com.example.util.Utility
import io.ktor.util.*
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit

class WaterRepository(
    db: CoroutineDatabase
) {
    val water = db.getCollection<Water>()
    val theTimestamp = System.currentTimeMillis()

    suspend fun getWaterQuantity(
        userId: String,
        timestamp: Long
    ): List<Long>{

        val date = Utility.formatTimestamp(timestamp)

        return water.find(
            and(
                Water::userId eq userId,
                Water::date eq date
            )
        )
            .toList()
            .map {
                it.quantity
            }
    }

    suspend fun addWater(
        userId: String,
        waterQty: Long,
        timestamp: Long
    ){
        val date = Utility.formatTimestamp(timestamp)

        water.insertOne(
            Water(
                userId, date, waterQty
            )
        )
    }
}