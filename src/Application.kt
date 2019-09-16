package com.birdTakehome

import com.birdTakehome.api.*
import com.birdTakehome.app.*
import com.birdTakehome.repository.*
import com.ryanharter.ktor.moshi.*
import dagger.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

@Component
internal interface AppComponent {
    val repository: EventsBirdsRepository
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(DefaultHeaders)

    install(StatusPages) {
        exception<Throwable>{ e ->
            call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }

    install(ContentNegotiation){
        moshi()
    }

    DatabaseFactory.init()
    val appComponent = DaggerAppComponent.create()
    val db = appComponent.repository

    routing {
        event(db)

        bird(db)

        birds(db)
    }
}

const val API_VERSION = "/api"

