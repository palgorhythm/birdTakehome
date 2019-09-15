package com.birdTakehome.model

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import java.io.Serializable
import java.util.*

class Event(id: EntityID<UUID>) : UUIDEntity(id) {
    var kind by Events.kind
    var lat by Events.lat
    var lng by Events.lng
    var timestamp by Events.timestamp
}

object Events : UUIDTable() {
    val kind: Column<String> = varchar("kind", 255)
    val bird_id: Column<EntityID<UUID>> = reference("bird_id", Birds).primaryKey()
    val lat: Column<Float> = float("lat")
    val lng: Column<Float> = float("lng")
    val timestamp: Column<Long> = long("timestamp")
}