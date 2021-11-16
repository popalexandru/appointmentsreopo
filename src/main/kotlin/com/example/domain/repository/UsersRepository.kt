package com.example.domain.repository

import com.example.data.models.TestDto
import com.example.data.models.User
import com.example.data.requests.CreateAccountRequest
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UsersRepository(
    db: CoroutineDatabase
) {
    private val users = db.getCollection<User>()

    suspend fun createAccount(
        user: User
    ){
        users.insertOne(user)
    }

    suspend fun getUserByEmail(
        userEmail: String
    ): User? {
        return users.findOne(User::email eq userEmail)
    }

    suspend fun doesPasswordForUserMatch(
        userEmail: String,
        userPassword: String
    ): Boolean{
        val user = getUserByEmail(userEmail)
        return user?.password == userPassword
    }

    suspend fun loginUser(
        userEmail: String,
        userPassword: String
    ): Boolean {
        return users.findOne(
            and(
                User::password eq userPassword,
                User::email eq  userEmail
            )
        ) != null
    }
}