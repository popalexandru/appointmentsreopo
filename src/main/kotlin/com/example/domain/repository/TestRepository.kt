package com.example.domain.repository

import org.litote.kmongo.coroutine.CoroutineDatabase

class TestRepository(
    db: CoroutineDatabase
) {

    private val testCategories = db.getCollection<String>()

    suspend fun addSmth(){
        testCategories.insertOne("Testee" + System.currentTimeMillis().toString())
    }
}