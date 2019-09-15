package com.birdTakehome.model

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import java.io.Serializable
import java.util.*

class Bird(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Bird>(Birds)
    var lat by Birds.lat
    var lng by Birds.lng
    var state by Birds.state
}

object Birds : UUIDTable() {
    val state = varchar("state", 255)
    val lat = float("lat")
    val lng = float("lng")
}