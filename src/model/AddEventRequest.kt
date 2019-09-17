package com.birdTakehome.model

data class AddEventRequest( // specify shape of request payload for addEvent
    val kind: String,
    val bird_id: String,
    val lat: Float,
    val lng: Float,
    val timestamp: Long
)