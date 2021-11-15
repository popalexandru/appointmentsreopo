package com.example.domain.service

import com.example.domain.repository.WaterRepository

class WaterService(
    private val repository: WaterRepository
) {
    suspend fun getWaterQuantity(
        userId: String,
        timestamp: Long): Long {
        var waterQty = 0L

        val waterEntries = repository.getWaterQuantity(userId, timestamp)

        for (entry in waterEntries){
            waterQty += entry
        }

        return waterQty
    }

    suspend fun addWater(
        userId: String,
        waterQty: Long,
        timestamp: Long
    ){
        repository.addWater(userId, waterQty, timestamp)
    }
}