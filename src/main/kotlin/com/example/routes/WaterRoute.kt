package com.example.routes

import com.example.data.requests.AddWaterReq
import com.example.domain.service.WaterService
import com.example.util.isValid
import com.example.util.userId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.waterRoute(
    waterService: WaterService
){
    post("api/water/add"){
        val request = call.receiveOrNull<AddWaterReq>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val userIdExists = true // @TODO: has to be done

        if(request.waterQty.isValid()){
            waterService.addWater(
                "123", request.waterQty, System.currentTimeMillis()
            )
            call.respond(
                HttpStatusCode.OK
            )
            return@post
        }else{
            call.respond(
                HttpStatusCode.BadRequest,
                "Wrong water value"
            )
            return@post
        }
    }

    get("api/test"){
        call.respondText { "Success" }

        waterService.addWater("123", 30, System.currentTimeMillis())
    }

    get("api/water/get"){
        val userId = call.userId()

        val userIdExists = true // @TODO: has to be done

        if(userIdExists){
            val waterQty = waterService.getWaterQuantity(userId, System.currentTimeMillis())

            call.respond(
                HttpStatusCode.OK,
                waterQty
            )
            return@get
        }

        call.respond(
            HttpStatusCode.NotFound,
            "User not found"
        )
        return@get
    }
}