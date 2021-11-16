package com.example

import com.example.others.di.mainModule
import io.ktor.application.*
import com.example.plugins.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.cancel
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

/*fun main(){
    embeddedServer(Netty, port = 8000){
        routing{
            get("/"){
                call.respondText { "Paul fac io serveru cu 3500" }
            }
        }
    }.start(wait = true)
}*/

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(Koin){
        modules(mainModule)
    }

    install(CORS){
            anyHost()
    }

    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
}
