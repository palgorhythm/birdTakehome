package com.birdTakehome.app

import com.birdTakehome.*
import com.birdTakehome.repository.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*


const val BIRDS = "$API_VERSION/birds"

fun Route.birds(db: Repository){
    get(BIRDS){
        val state = call.request.queryParameters["state"] ?: ""
        val birds = db.getBirdsByState(state)
        call.respond(birds)
    }
}