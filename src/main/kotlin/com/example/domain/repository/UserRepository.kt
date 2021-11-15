package com.example.domain.repository

import com.example.data.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserRepository(
    db: CoroutineDatabase
) {
    private val users = db.getCollection<User>()

    suspend fun createUser(user: User) {
        users.insertOne(user)
    }

    suspend fun getUserById(id: String): User? {
        return users.findOneById(id)
    }

    suspend fun getUserByEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }

    suspend fun doesPasswordForUserMatch(
        email: String,
        enteredPassword: String
    ): Boolean {
        val user = getUserByEmail(email)
        return user?.password == enteredPassword
    }

    suspend fun getUserByUsername(username: String): User? {
        return users.findOne(User::username eq username)
    }

    suspend fun doesEmailBelongsToUserId(email: String, userId: String): Boolean {
        return users.findOneById(userId)?.email == email
    }
}