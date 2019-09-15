package com.birdTakehome.model

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import java.io.Serializable

data class Event(val kind: String, val bird_id: String, val lat: Float, val lng: Float) : Serializable{
    var id : Int? = null
    var timestamp: Long? = null
}

object Events : IntIdTable() {
    val kind: Column<String> = varchar("kind", 255)
    val bird_id: Column<String> = varchar("bird_id",255)
    val lat: Column<Float> = float("lat")
    val lng: Column<Float> = float("lng")
    val timestamp: Column<Long> = long("timestamp")
}