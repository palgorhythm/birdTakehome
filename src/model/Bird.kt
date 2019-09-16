package com.birdTakehome.model

import org.jetbrains.exposed.dao.*

data class Bird(
    val id: String,
    val lat: Float,
    val lng: Float,
    val state: String
)

data class BirdWithEvents(
    val id: String,
    val lat: Float,
    val lng: Float,
    val state: String,
    var events: Array<Event>
)

object Birds : UUIDTable() {
    val state = varchar("state", 255)
    val lat = float("lat")
    val lng = float("lng")
}