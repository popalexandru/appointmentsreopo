package com.example.routes

import com.example.domain.repository.BusinessRepository
import com.example.domain.repository.BusinessSnippetRepository
import com.example.domain.repository.ReservationRepository
import com.example.domain.repository.ServiceRepository
import com.example.util.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.businessRoute(
    repository: BusinessSnippetRepository,
    businessRepository: BusinessRepository,
    reservationRepository: ReservationRepository,
    serviceRepository: ServiceRepository
) {
    authenticate {
        get("api/business/get")
        {
            val businessId = call.businessId
            val userId = call.userIdToken

/*            val reservation = reservationRepository.getReservationByUserAndBusiness(
                userId, businessId
            )*/
            val allReservations = reservationRepository.getReservationsByBusinessId(businessId)
            allReservations.forEach {
                if(it.userId == userId)
                    it.userId = "-1"
            }

            val servicesList = serviceRepository.getServicesByBusiness(businessId)

            val business = businessRepository.getBusinessById(businessId)
            if (business != null) {
                business.allReservations = allReservations

                servicesList.isNotEmpty().let {
                    business.servicesList = servicesList
                }
            }

            business?.let {
                call.respond(
                    HttpStatusCode.OK,
                    business
                )
                return@get
            }

            call.respond(
                HttpStatusCode.NotFound
            )
        }
    }

    get("api/business/snippets/get")
    {
        val query = call.query()
        val page = call.page()
        val pageSize = call.pageSize()

        val businessSnippetsList = repository.getBusinessSnippets(
            query, page, pageSize
        )

        call.respond(
            HttpStatusCode.OK,
            businessSnippetsList
        )
    }

}