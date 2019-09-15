package com.birdTakehome.repository

import com.birdTakehome.model.*
import org.jetbrains.exposed.sql.*

class EventsBirdsRepository : Repository {
    override suspend fun addEvent(event: Event) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getBirdById(birdId: String): Bird? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getBirdsByState(state: String): List<Bird>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun toEvent(row: ResultRow): Event =
        Event(id = row[Events.id].value, )
}