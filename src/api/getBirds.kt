package com.birdTakehome.app

import com.birdTakehome.*
import com.birdTakehome.model.*
import com.birdTakehome.repository.*
import com.squareup.moshi.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import com.squareup.moshi.Types.newParameterizedType
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlin.reflect.*


const val BIRDS = "$API_VERSION/birds"

fun Route.birds(db: Repository){
    get(BIRDS){
        val state = call.request.queryParameters["state"] ?: ""
        val birds = db.getBirdsByState(state)
        call.respond(birds)
    }
}