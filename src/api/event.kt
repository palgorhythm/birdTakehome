package com.birdTakehome.api

import com.birdTakehome.*
import com.birdTakehome.model.*
import com.birdTakehome.repository.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val EVENT_ENDPOINT = "$API_VERSION/event"

fun Route.event(db: Repository){
    post(EVENT_ENDPOINT){
        val request = call.receive<Request>()
        val event = db.addEvent(Event(request.kind, request.bird_id, request.lat, request.lng))
        call.respond(event)
    }
}