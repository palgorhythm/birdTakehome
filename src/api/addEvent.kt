package com.birdTakehome.api

import com.birdTakehome.model.*
import com.birdTakehome.db.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

const val EVENT_ENDPOINT = "api/event"

fun Route.event(db: DB){ // extension function for Route that a requests may be routed to
    post(EVENT_ENDPOINT){
        val request = call.receive<AddEventRequest>() //receive requests whose body conforms to AddEventRequest data class
        db.addEvent(request.kind, request.bird_id, request.lat, request.lng, request.timestamp) //add to db
        call.respond(HttpStatusCode.OK, "event added!")
    }
}