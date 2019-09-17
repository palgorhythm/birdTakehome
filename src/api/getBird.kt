package com.birdTakehome.api

import com.birdTakehome.db.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

const val BIRD_ENDPOINT = "api/bird/{id}"

fun Route.bird(db: DB){
    get(BIRD_ENDPOINT){
        val pathParam = call.parameters["id"] ?: "" //grab the path param holding the bird's UUID
        val bird = db.getBirdById(pathParam) //DB call
        if (bird != null) {
            call.respond(bird)
        } else {
            call.respond(HttpStatusCode.OK, "no birds with provided UUID found")
        }
    }
}