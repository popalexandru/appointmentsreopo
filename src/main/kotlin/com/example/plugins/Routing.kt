package com.example.plugins

import com.example.domain.repository.WorkoutDayRepository
import com.example.domain.service.*
import com.example.routes.*
import io.ktor.routing.*
import io.ktor.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val waterService : WaterService by inject()
    val workoutService : WorkoutService by inject()
    val userService: UserService by inject()
    val repetitionService: RepetitionService by inject()
    val exampleService: ExampleService by inject()
    val exerciceService: ExerciceService by inject()

    routing {
        waterRoute(waterService)
        workoutRoute(workoutService, userService)
        loginUser(userService)
        createUserRoute(userService)
        workoutDayRoute(
            userService,
            repetitionService,
            waterService,
            workoutService,
            exampleService,
            exerciceService
        )
    }
}
