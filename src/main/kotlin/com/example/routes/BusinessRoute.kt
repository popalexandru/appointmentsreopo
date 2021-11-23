package com.example.routes

import com.example.domain.repository.BusinessRepository
import com.example.domain.repository.BusinessSnippetRepository
import com.example.util.businessId
import com.example.util.page
import com.example.util.pageSize
import com.example.util.query
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.businessRoute(
    repository: BusinessSnippetRepository,
    businessRepository: BusinessRepository
) {
    get("api/business/get")
    {
        val businessId = call.businessId

        val business = businessRepository.getBusinessById(businessId)

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