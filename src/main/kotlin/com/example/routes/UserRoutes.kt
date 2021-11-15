package com.example.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.data.models.User
import com.example.data.requests.CreateAccountRequest
import com.example.data.requests.LoginRequest
import com.example.domain.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.createUserRoute(
    loginService: UserService
){
    route("/api/user/create"){

        post {
            val request = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val userExist = loginService.getUserByEmail(request.email) != null

            if(request.email.isBlank() || request.password.isBlank() || request.username.isBlank()){
                call.respond(
                    "BasicApiResponse(ApiResponseMessages.FIELDS_BLANK, false)"
                )
                return@post
            }

            if(userExist){
                call.respond(
                    "BasicApiResponse(ApiResponseMessages.USER_ALREADY_EXISTS, false)"
                )
                return@post
            }else{
                val userNameExist = loginService.getUserByUsername(request.username) != null
                if(userNameExist){
                    call.respond(
                        "BasicApiResponse(ApiResponseMessages.USERNAME_EXISTS, false)"
                    )
                }
            }

            loginService.createUser(
                User(
                    email = request.email,
                    username = request.username,
                    password = request.password,
                    profileImageUrl = ""
                )
            )
            call.respond(
                "BasicApiResponse(successful = true)"
            )
        }
    }
}

fun Route.loginUser(
    loginService: UserService/*,
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String*/
){
    post("/api/user/login"){
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if(request.email.isBlank() || request.password.isBlank()){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val isCorrectPassword = loginService.doesPasswordForUserMatch(request)

        if(isCorrectPassword){
            val expiresIn = 1000L * 60L * 60L * 24L * 365L
/*            val token = JWT.create()
                .withClaim("email", request.email)
                .withIssuer(jwtIssuer)
                .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
                .withAudience(jwtAudience)
                .sign(Algorithm.HMAC256(jwtSecret))*/


            call.respond(
                HttpStatusCode.OK,
                /*AuthResponse(token = token, successful = true)*/
            )
        }else{
            call.respond(
                HttpStatusCode.OK,
                /*AuthResponse(successful = false)*/
            )
        }
    }
}

fun Route.authenticate(){
    authenticate {
        get("/api/user/authenticate"){
            call.respond(HttpStatusCode.OK)
        }
    }
}