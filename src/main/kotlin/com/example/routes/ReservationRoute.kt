package com.example.routes

import com.example.data.requests.LoginRequest
import com.example.data.requests.ReservationRequest
import com.example.domain.repository.BusinessRepository
import com.example.domain.repository.ReservationRepository
import com.example.domain.repository.UsersRepository
import com.example.util.businessId
import com.example.util.userIdToken
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.makeReservation(
    usersRepository: UsersRepository,
    reservationRepository: ReservationRepository,
    businessRepository: BusinessRepository
){
    authenticate {
        post("api/reservation/make"){
            val request = call.receiveOrNull<ReservationRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val userId = call.userIdToken

            val userExists = usersRepository.getUserById(userId) != null

            if(userExists){
                val business = businessRepository.getBusinessById(request.restaurantId)

                business?.let {
                    val userReservation = reservationRepository.getReservationByUserId(userId)

                    userReservation?.let {
                        call.respond(HttpStatusCode.NotFound)
                        return@post
                    }

                    val insertResult = reservationRepository.makeReservation(userId, request.restaurantId, it.businessName)

                    if(insertResult.wasAcknowledged()){
                        call.respond(HttpStatusCode.OK)
                    }else{
                        call.respond(HttpStatusCode.NotFound)
                    }
                    return@post
                }

                call.respond(HttpStatusCode.NotFound)
                return@post
            }else{
                call.respond(HttpStatusCode.NotFound)
                return@post
            }
        }
    }
}

fun Route.getReservation(
    usersRepository: UsersRepository,
    reservationRepository: ReservationRepository
){
    authenticate {
        get("api/reservation/get/byuser")
        {
            val userId = call.userIdToken

            val user = usersRepository.getUserById(userId)

            if(user != null)
            {
                val reservation = reservationRepository.getReservationByUserId(userId)

                reservation?.let {
                    call.respond(
                        HttpStatusCode.OK,
                        reservation
                    )
                    return@get
                }

                call.respond(HttpStatusCode.OK)
                return@get
            }else
            {
                call.respond(
                    HttpStatusCode.NotFound
                )
            }
        }
    }
}