package com.birdTakehome.api

import com.birdTakehome.*
import com.birdTakehome.model.*
import com.birdTakehome.repository.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

const val EVENT_ENDPOINT = "$API_VERSION/event"

fun Route.event(db: Repository){
    post(EVENT_ENDPOINT){
        val request = call.receive<Request>()
        db.addEvent(request.kind, UUID.fromString(request.bird_id), request.lat, request.lng)
        call.respond(HttpStatusCode.OK, "event added!")
    }
}