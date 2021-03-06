package com.example.routes

import com.example.data.models.ReservationWithService
import com.example.data.requests.LoginRequest
import com.example.data.requests.ReservationCancelRequest
import com.example.data.requests.ReservationRequest
import com.example.domain.repository.BusinessRepository
import com.example.domain.repository.ReservationRepository
import com.example.domain.repository.ServiceRepository
import com.example.domain.repository.UsersRepository
import com.example.util.businessId
import com.example.util.userIdToken
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.deleteReservation(
    reservationRepository: ReservationRepository
){
    authenticate {
        post("api/reservation/delete"){
            val request = call.receiveOrNull<ReservationCancelRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val ack = reservationRepository.cancelReservation(request.reservationId)

            if(ack.wasAcknowledged()){
                call.respond(HttpStatusCode.OK)
                return@post
            }else{
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
        }
    }
}

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
                    /* val userReservation = reservationRepository.getReservationByUserId(userId)

                   userReservation?.let {
                        call.respond(HttpStatusCode.NotFound)
                        return@post
                    }*/

                    val insertResult = reservationRepository.makeReservation(
                        userId, request.restaurantId, it.businessName, request.timestamp, request.serviceId)

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
    reservationRepository: ReservationRepository,
    serviceRepository: ServiceRepository
){
    authenticate {
        get("api/reservation/get/byuser")
        {
            val userId = call.userIdToken

            val user = usersRepository.getUserById(userId)

            if(user != null)
            {

                val reservationsWithServices = mutableListOf<ReservationWithService>()

                val reservations = reservationRepository.getReservationsByUserId(userId)
                reservations.forEach { reservation ->
                    val service = serviceRepository.getServiceById(reservation.serviceId)

                    service?.let {
                        val reservWithServ = reservation.toReservationWithService(it)
                        reservationsWithServices.add(reservWithServ)
                    }
                }

                call.respond(
                    HttpStatusCode.OK,
                    reservationsWithServices
                )

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

fun Route.getReservationByBandU(
    reservationRepository: ReservationRepository,
    usersRepository: UsersRepository
){
    authenticate {
        get("api/reservation/get/byservice/bybusiness"){
            val reservations = reservationRepository.getReservationsByBusinessId(call.businessId)

            call.respond(HttpStatusCode.OK, reservations)
        }
    }
}