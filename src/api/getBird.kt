package com.birdTakehome.api

import com.birdTakehome.db.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

const val BIRD_ENDPOINT = "api/bird/{id}"

fun Route.bird(db: DB){
    get(BIRD_ENDPOINT){
        val pathParam = call.parameters["id"] ?: ""
        val bird = db.getBirdById(UUID.fromString(pathParam))
        if (bird != null) {
            call.respond(bird)
        }
    }
}