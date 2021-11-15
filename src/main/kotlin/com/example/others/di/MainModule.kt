package com.example.others.di

import com.example.domain.repository.*
import com.example.domain.service.*
import com.example.others.constants.Constants
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo



val mainModule = module {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(Constants.DB_NAME)
    }

    single{
        WorkoutDayRepository(get())
    }

    single{
        WorkoutRepository(get())
    }

    single{
        ExamplesRepository(get())
    }

    single{
        ExerciceRepository(get())
    }

    single{
        RepetitionRepository(get())
    }

    single{
        WaterRepository(get())
    }

    single{
        UserRepository(get())
    }

    single {
        WorkoutService(get())
    }

    single {
        ExampleService(get())
    }
    single {
        ExerciceService(get())
    }
    single {
        RepetitionService(get())
    }
    single {
        WaterService(get())
    }

    single {
        UserService(get())
    }

/*    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single<RestaurantSnippetRepository>{
        RestaurantSnippetRepoImpl(get())
    }
    single<RestaurantRepository>{
        RestaurantRepositoryImpl(get())
    }
    single<ReservationRepository>{
        ReservationRepositoryImpl(get())
    }
    single<ReviewRepository>{
        ReviewRepository(get())
    }

    single {
        TableRepository(get())
    }

    single{
        LoginService(get())
    }

    single {
        TableService(get())
    }

    single{
        RestaurantService(get(), get())
    }

    single{
        ReservationService(get())
    }

    single {
        ReviewService(get())
    }*/
}