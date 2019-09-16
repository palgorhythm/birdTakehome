package com.birdTakehome.model

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import java.io.Serializable
import java.util.*

data class Event(
    val id: String,
    val bird_id: String,
    val lat: Float,
    val lng: Float,
    val kind: String,
    val timestamp: Long
)

object Events : UUIDTable() {
    val bird_id: Column<EntityID<UUID>> = reference("bird_id", Birds).primaryKey()
    val lat: Column<Float> = float("lat")
    val lng: Column<Float> = float("lng")
    val kind: Column<String> = varchar("kind", 255)
    val timestamp: Column<Long> = long("timestamp")
}