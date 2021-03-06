package com.example.plugins

import com.example.domain.repository.*
import com.example.domain.service.*
import com.example.routes.*
import io.ktor.routing.*
import io.ktor.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret= environment.config.property("jwt.secret").getString()

    val userService: UserService by inject()
    val usersRepository: UsersRepository by inject()
    val reservationRepository: ReservationRepository by inject()
    val businessReservationRepository : BusinessSnippetRepository by inject()
    val businessRepository : BusinessRepository by inject()
    val servicesRepository : ServiceRepository by inject()

    routing {
        loginUser(
            userService,
            jwtIssuer, jwtAudience, jwtSecret
        )
        createUserRoute(userService)
        testRoute()
        makeReservation(usersRepository, reservationRepository, businessRepository)
        authRoute()
        getUser(loginService = userService)
        businessRoute(businessReservationRepository, businessRepository, reservationRepository, servicesRepository)

        /* Reservation */
        getReservation(usersRepository, reservationRepository, servicesRepository)
        getUserWithReservation(userService, reservationRepository)
        deleteReservation(reservationRepository)

/*        workoutDayRoute(
            userService,
            repetitionService,
            waterService,
            workoutService,
            exampleService,
            exerciceService
        )*/
        /*        waterRoute(waterService)
        workoutRoute(workoutService, userService)*/
    }
}
