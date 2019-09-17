package api

import com.birdTakehome.model.*
import com.birdTakehome.db.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

const val EVENT_ENDPOINT = "api/event"

fun Route.event(db: DB){
    post(EVENT_ENDPOINT){
        val request = call.receive<Request>()
        db.addEvent(request.kind, UUID.fromString(request.bird_id), request.lat, request.lng)
        call.respond(HttpStatusCode.OK, "event added!")
    }
}