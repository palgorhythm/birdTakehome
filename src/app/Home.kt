package com.birdTakehome.app

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.text.get

const val HOME = "/"

fun Route.home(){
    get(HOME){
        call.respondText("hello, world!")
    }
}