package com.example.domain.service

import com.example.data.models.User
import com.example.data.requests.CreateAccountRequest
import com.example.domain.repository.UsersRepository
import com.mongodb.client.result.InsertOneResult

class UserService(
    private val userRepository: UsersRepository
) {


    suspend fun createAccount(request: CreateAccountRequest): InsertOneResult{
        return userRepository.createAccount(
            User(
                request.email,
                "",
                request.password
            )
        )
    }

    suspend fun getUserByEmail(
        userEmail: String
    ): User? {
        return userRepository.getUserByEmail(userEmail)
    }

    suspend fun getUserById(
        userId: String
    ): User? {
        return userRepository.getUserById(userId)
    }


    suspend fun doesPasswordForUserMatch(
        userEmail: String,
        userPassword: String
    ): Boolean{
        return userRepository.doesPasswordForUserMatch(
            userEmail, userPassword
        )
    }

    suspend fun loginUser(
        userEmail: String,
        userPassword: String
    ): Boolean {
        return userRepository.loginUser(
            userEmail, userPassword
        )
    }
}