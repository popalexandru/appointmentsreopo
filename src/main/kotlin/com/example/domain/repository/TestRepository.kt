package com.example.domain.repository

import com.example.data.models.TestDto
import com.example.data.requests.CreateAccountRequest
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class TestRepository(
    db: CoroutineDatabase
) {

    private val testCategories = db.getCollection<TestDto>()
    private val logins = db.getCollection<CreateAccountRequest>()

    suspend fun addSmth(){
        testCategories.insertOne(TestDto(text = System.currentTimeMillis().toString()))
    }

    suspend fun createAccount(
        username: String,
        password : String,
        email: String
    ){
        logins.insertOne(
            CreateAccountRequest(
                email, username, password
            )
        )
    }

    suspend fun getPassword(username: String): String?{
        return logins.findOne(CreateAccountRequest::username eq username)?.password
    }
}