package com.example.routes

import com.example.data.requests.LoginRequest
import com.example.data.requests.ReservationRequest
import com.example.domain.repository.ReservationRepository
import com.example.domain.repository.UsersRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.makeReservation(
    usersRepository: UsersRepository,
    reservationRepository: ReservationRepository
){
    authenticate {
        post("api/reservation/make"){
            val request = call.receiveOrNull<ReservationRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val userExists = usersRepository.getUserById(request.userId) != null

            if(userExists){
                reservationRepository.makeReservation(request.userId)
                call.respond(HttpStatusCode.OK)
                return@post
            }else{
                call.respond(HttpStatusCode.NotFound)
                return@post
            }
        }
    }
}