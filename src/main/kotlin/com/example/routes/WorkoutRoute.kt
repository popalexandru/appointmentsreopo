package com.example.routes

import com.example.data.requests.CreateAccountRequest
import com.example.data.requests.WorkoutFinishRequest
import com.example.domain.service.UserService
import com.example.domain.service.WorkoutService
import com.example.util.userId
import com.example.util.workoutId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.workoutRoute(
    workoutService: WorkoutService,
    userService: UserService
){
    get("api/workout/get"){
        val userId = call.userId()

        val userIdExists = userService.getUserById(userId) != null

        if(userIdExists){
            val workout = workoutService.getWorkout(
                userId,
                System.currentTimeMillis()
            )

            if(workout != null){
                call.respond(
                    HttpStatusCode.OK,
                    workout
                )
                return@get
            }else{
                workoutService.addEmptyWorkout(
                    userId,
                    System.currentTimeMillis()
                )
                call.respond(
                    HttpStatusCode.OK,
                    "Workout created"
                )
                return@get
            }
        }else{
            call.respond(
                HttpStatusCode.BadRequest,
                "User not found"
            )
        }
    }

    post("api/workout/finish"){
        val request = call.receiveOrNull<WorkoutFinishRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val workout = workoutService.finishWorkout(request.workoutId)

        call.respond(
            HttpStatusCode.OK,
            "Updated"
        )
    }
}