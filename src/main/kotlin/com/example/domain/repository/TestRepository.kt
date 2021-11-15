package com.example.domain.repository

import com.example.data.models.TestDto
import org.litote.kmongo.coroutine.CoroutineDatabase

class TestRepository(
    db: CoroutineDatabase
) {

    private val testCategories = db.getCollection<TestDto>()

    suspend fun addSmth(){
        testCategories.insertOne(TestDto(text = System.currentTimeMillis().toString()))
    }
}