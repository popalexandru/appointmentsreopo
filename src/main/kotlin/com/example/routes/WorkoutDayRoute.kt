package com.example.routes

import com.example.data.final_models.Exercice
import com.example.data.final_models.Rep
import com.example.data.final_models.WorkoutDay
import com.example.domain.service.*
import com.example.util.timestamp
import com.example.util.userId
import com.example.util.workoutId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

/*
fun Route.workoutDayRoute(
    userService: UserService,
    repetitionService: RepetitionService,
    waterService: WaterService,
    workoutService: WorkoutService,
    exampleService: ExampleService,
    exerciceService: ExerciceService
){
    get("api/workoutday/get"){
        val userId = call.userId()
        val timestamp = call.timestamp()

        userService.getUserById(userId)?.let {
            val workout = workoutService.getWorkout(userId, timestamp)

            workout?.let {
                val exercicesIdsList = exerciceService.getExercicesByWorkout(workout.workoutId)
                val exercicesList = mutableListOf<Exercice>()

                for(ex in exercicesIdsList){
                    exampleService.getExById(ex.exerciceId)?.let { exe ->
                        val reps = repetitionService.getRepetitions(
                            workout.workoutId,
                            ex.exerciceId
                        ).map {
                            Rep(it.reps, it.weight)
                        }

                        exercicesList.add(
                            Exercice(
                                exe.exId,
                                exe.exName,
                                exe.exDesc,
                                exe.exImg,
                                reps
                            )
                        )
                    }
                }

                val waterQty = waterService.getWaterQuantity(userId, timestamp)

                call.respond(
                    HttpStatusCode.OK,
                    WorkoutDay(
                        exercicesList,
                        waterQty,
                        workout.isDone
                    )
                )
            }

            call.respond(
                HttpStatusCode.OK,
                "No workout found"
            )
            return@get
        }

        call.respond(
            HttpStatusCode.BadRequest,
            "User not found"
        )
        return@get
    }
}*/
