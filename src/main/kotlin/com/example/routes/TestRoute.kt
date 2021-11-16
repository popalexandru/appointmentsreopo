package com.example.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.testRoute(){
    get("/servertest"){
        call.respondText { "Mere serveru de il fute fdumnezeu" }
    }
}