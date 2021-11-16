package com.example.domain.service

import com.example.data.models.User
import com.example.data.requests.CreateAccountRequest
import com.example.domain.repository.UsersRepository

class UserService(
    private val userRepository: UsersRepository
) {


    suspend fun createAccount(request: CreateAccountRequest){
        userRepository.createAccount(
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