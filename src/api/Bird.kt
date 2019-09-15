package com.birdTakehome.app

import com.birdTakehome.*
import com.birdTakehome.repository.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

const val BIRD = "$API_VERSION/bird/{id}"

fun Route.bird(db: Repository){
    get(BIRD){
        val pathParam = call.parameters["id"] ?: ""
        val bird = db.getBirdById(pathParam)
        if (bird != null) {
            call.respond(bird)
        }
    }
}