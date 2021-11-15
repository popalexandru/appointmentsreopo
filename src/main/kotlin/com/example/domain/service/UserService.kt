package com.example.domain.service

import com.example.data.models.User
import com.example.data.requests.LoginRequest
import com.example.domain.repository.UserRepository

class UserService(
    private val userRepository: UserRepository
) {
    suspend fun createUser(user: User) {
        userRepository.createUser(user)
    }

    suspend fun getUserById(userId: String): User? {
        return userRepository.getUserById(userId)
    }

    suspend fun getUserByEmail(userEmail: String): User? {
        return userRepository.getUserByEmail(userEmail)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userRepository.getUserByUsername(username)
    }

    suspend fun doesPasswordForUserMatch(request: LoginRequest): Boolean {
        return userRepository.doesPasswordForUserMatch(
            email = request.email,
            enteredPassword = request.password
        )
    }

    suspend fun doesEmailBelongToUID(email: String, userId: String): Boolean {
        return userRepository.doesEmailBelongsToUserId(email, userId)
    }
}