package com.birdTakehome

import api.*
import com.birdTakehome.api.*
import com.birdTakehome.db.*
import com.ryanharter.ktor.moshi.*
import dagger.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*

@Component
internal interface AppComponent {
    val repository: InMemoryDatabase
}

fun main(args: Array<String>): Unit = EngineMain.main(args) // initializes & starts the Netty server

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
// extension function to Application that sets up error handling, serialization, routes, etc.

    install(DefaultHeaders)

    install(StatusPages) { // error handling
        exception<Throwable>{ e ->
            call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }

    install(ContentNegotiation){
        // enable moshi for content conversion (JSON parsing & serialization)
        moshi()
    }

    DatabaseInitializer.init()
    val appComponent = DaggerAppComponent.create() // inject dependencies
    val db = appComponent.repository //grab the db component

    routing {
        event(db) // initialize our routes to use whatever db we chose

        bird(db)

        birds(db)
    }
}

