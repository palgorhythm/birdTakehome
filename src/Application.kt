package com.birdTakehome

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
    val db: EventsBirdsDatabase
}

fun main(args: Array<String>): Unit = EngineMain.main(args) // initializes & starts the Netty server

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
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
    val appComponent = DaggerAppComponent.create() // inject dependencies
    val db = appComponent.db //grab the db component

    if(db.needToInitialize) DatabaseInitializer.init()

    routing {
        event(db) // initialize our routes to use whatever db we chose

        bird(db)

        birds(db)
    }
}

