package com.example.util

import com.example.others.constants.Constants
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Long.isValid() : Boolean{
    if(this in 1..999){
        return true
    }
    return false
}

fun ApplicationCall.waterQty(): Long {
    return this.parameters[Constants.WATER_PARAM]?.toLongOrNull() ?: 0L
}

fun ApplicationCall.userId(): String{
    return this.parameters[Constants.USER_ID_PARAM] ?: ""
}

fun ApplicationCall.workoutId(): String{
    return this.parameters[Constants.WORKOUT_ID_PARAM] ?: ""
}

fun ApplicationCall.timestamp(): Long{
    return this.parameters[Constants.TIMESTAMP_PARAM]?.toLongOrNull() ?: System.currentTimeMillis()
}

val ApplicationCall.userIdToken: String
    get() = principal<JWTPrincipal>()?.getClaim("userId", String::class) ?: ""

val ApplicationCall.businessId: String
    get() = this.parameters[Constants.PARAM_BS_ID] ?: ""

fun ApplicationCall.page(): Int{
    val page = this.parameters[Constants.PARAM_PAGE]?.toIntOrNull() ?: 0
    return page - 1
}

fun ApplicationCall.pageSize(): Int{
    return this.parameters[Constants.PARAM_PAGE_SIZE]?.toIntOrNull() ?: 3
}

fun ApplicationCall.query(): String{
    return this.parameters[Constants.PARAM_QUERY] ?: ""
}