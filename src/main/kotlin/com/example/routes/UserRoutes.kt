package com.example.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.data.models.User
import com.example.data.models.UserWithReservation
import com.example.data.requests.CreateAccountRequest
import com.example.data.requests.LoginRequest
import com.example.data.responses.AuthResponse
import com.example.data.responses.RegisterResponse
import com.example.domain.repository.ReservationRepository
import com.example.domain.service.UserService
import com.example.util.userIdToken
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
                    HttpStatusCode.OK,
                    RegisterResponse(
                        successful = false,
                        fieldsAreBlank = true
                    )
                )
                return@post
            }

            if(userExist){
                call.respond(
                    HttpStatusCode.OK,
                    RegisterResponse(
                        successful = true,
                        userAlreadyExists = true
                    )
                )
                return@post
            }

            val result = loginService.createAccount(
                CreateAccountRequest(
                    name = request.name,
                    surname = request.surname,
                    email = request.email,
                    password = request.password
                )
            )

/*            if(result.wasAcknowledged()){
                call.respondText { "Response not acknowledged" }
            }else{*/
                call.respond(
                    HttpStatusCode.OK,
                    RegisterResponse(
                        successful = true
                    )
                )
            /*}*/
        }
    }
}

fun Route.loginUser(
    loginService: UserService,
    jwtIssuer: String,
    jwtAudience: String,
    jwtSecret: String
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

        val user = loginService.getUserByEmail(request.email) ?: kotlin.run {
            call.respond(
                HttpStatusCode.OK,
                AuthResponse(
                    userDoesntExist = true
                )
            )
            return@post
        }

        val isCorrectPassword = loginService.doesPasswordForUserMatch(
            request.email,
            request.password
        )

        if(isCorrectPassword){
            val expiresIn = 1000L * 60L * 60L * 24L * 365L
            val token = JWT.create()
                .withClaim("userId", user.id)
                .withIssuer(jwtIssuer)
                .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
                .withAudience(jwtAudience)
                .sign(Algorithm.HMAC256(jwtSecret))


            call.respond(
                HttpStatusCode.OK,
                AuthResponse(token = token, successful = true)
            )
        }else{
            val userExists = loginService.getUserByEmail(request.email) != null

            if(userExists){
                call.respond(
                    HttpStatusCode.OK,
                    AuthResponse(wrongPassword = true)
                )
            }
            else{
                call.respond(
                    HttpStatusCode.OK,
                    AuthResponse(userDoesntExist = true)
                )
            }
        }
    }
}

fun Route.getUser(
    loginService: UserService
){
    authenticate {
        get("api/get/user"){
            val userId = call.userIdToken

            val user = loginService.getUserById(userId)

            user?.let {

                println("User found !")
                call.respond(
                    HttpStatusCode.OK,
                    user
                )
                return@get
            }

            call.respond(
                HttpStatusCode.NotFound
            )
            return@get
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

fun Route.getUserWithReservation(
    userService: UserService,
    reservationRepository: ReservationRepository
){
    authenticate {
        get("api/get/user/withr")
        {
            val user = userService.getUserById(call.userIdToken)

            user?.let {
                val userWithReservation = userToUserR(it)

                val userReservation = reservationRepository.getReservationByUserId(call.userIdToken)

                userReservation?.let {
                    /* add the reservation if exists */
                    userWithReservation.reservation = it
                }

                call.respond(
                    HttpStatusCode.OK,
                    userWithReservation
                )
            }

            /* user doesnt exist */
            call.respond(HttpStatusCode.NotFound)
        }
    }
}

private fun userToUserR(user: User) : UserWithReservation {
    return UserWithReservation(
        user.name,
        user.surname,
        user.email,
        user.profileImageUrl,
        null
    )
}