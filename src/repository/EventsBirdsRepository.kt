package com.birdTakehome.repository

import com.birdTakehome.model.*
import com.birdTakehome.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.*
import java.util.*

class EventsBirdsRepository : Repository {
    override suspend fun addEvent(kindInput: String, bird_idInput: UUID, latInput: Float, lngInput: Float) {
        transaction{
            Birds.select{Birds.id eq bird_idInput}
        }
        transaction {
            val bird = Birds.select{Birds.id eq bird_idInput}.mapNotNull{ toBird(it)}.singleOrNull()
            Birds.insertOrUpdate(Birds.id){
                it[id] = EntityID(bird_idInput, Birds)
                it[lat] = latInput
                it[lng] = lngInput
                it[state] = if(kindInput == "ride_start") "in_ride" else "idle"
            }
            Events.insert{
                it[kind] = kindInput
                it[bird_id] = EntityID(bird_idInput, Birds)
                it[lat] = latInput
                it[lng] = lngInput
                it[timestamp] = Timestamp(System.currentTimeMillis()).time
            }
        }
    }

    override suspend fun getBirdById(birdId: UUID): Bird? = dbQuery {
        Birds.select{
            Birds.id eq EntityID(birdId, Birds)
        }.mapNotNull{ toBird(it)}.singleOrNull()
    // need to add joining of events here!!!
    }

    override suspend fun getBirdsByState(state: String): List<Bird>? = dbQuery {
        Birds.select{ Birds.state eq state}.mapNotNull{toBird(it)}
    }

    private fun toEvent(row: ResultRow): Event =
        Event(id = EntityID(row[Events.id].value, Events))

    private fun toBird(row: ResultRow): Bird =
        Bird(id = EntityID(row[Birds.id].value, Birds))
}