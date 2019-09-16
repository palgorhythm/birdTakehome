package com.birdTakehome.model

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import java.io.Serializable
import java.util.*

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