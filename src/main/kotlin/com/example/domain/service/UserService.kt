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
                name = request.name,
                surname = request.surname,
                email = request.email,
                password = request.password,
                profileImageUrl = "https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg"
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