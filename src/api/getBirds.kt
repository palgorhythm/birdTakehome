package com.birdTakehome.api

import com.birdTakehome.db.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*


const val BIRDS_ENDPOINT = "api/birds"

fun Route.birds(db: DB){
    get(BIRDS_ENDPOINT){
        val state = call.request.queryParameters["state"] ?: "" //in_ride or idle
        val birds = db.getBirdsByState(state) // Array<Bird> in provided state
        call.respond(birds)
    }
}