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

            if(request.email.isBlank() || request.password.isBlank()){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Fields can't be blank"
                )
                return@post
            }

            if(userExist){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Utilizatorul exista deja"
                )
                return@post
            }

            loginService.createAccount(
                CreateAccountRequest(
                    email = request.email,
                    password = request.password
                )
            )
            call.respond(
                HttpStatusCode.OK
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
            call.respond(
                HttpStatusCode.BadRequest,
                "Fields can't be blank"
            )
            return@post
        }

        val isCorrectPassword = loginService.doesPasswordForUserMatch(
            request.email,
            request.password
        )

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
                "Logat"
                /*AuthResponse(token = token, successful = true)*/
            )
        }else{
            val userExists = loginService.getUserByEmail(request.email) != null

            if(userExists){
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Parola incorecta"
                    /*AuthResponse(successful = false)*/
                )
            }
            else{
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Utilizatorul nu exista"
                    /*AuthResponse(successful = false)*/
                )
            }


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