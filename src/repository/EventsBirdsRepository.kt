package com.birdTakehome.repository

import com.birdTakehome.model.*
import com.birdTakehome.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.*
import java.util.*
import javax.inject.*

class EventsBirdsRepository @Inject constructor(): Repository {
    override suspend fun addEvent(kindInput: String, bird_idInput: UUID, latInput: Float, lngInput: Float){
        require(kindInput == "ride_start" || kindInput == "ride_end") { "not a valid event kind!" }
        transaction{
            Birds.select{Birds.id eq bird_idInput}
        }
        transaction {
            val curTimestamp = Timestamp(System.currentTimeMillis()).time
            val birdEntityId = EntityID(bird_idInput, Birds)
            Birds.insertOrUpdate(Birds.id){
                it[id] = birdEntityId
                it[lat] = latInput
                it[lng] = lngInput
                it[state] = if(kindInput == "ride_start") "in_ride" else "idle"
            }
            Events.insert{
                it[kind] = kindInput
                it[bird_id] = birdEntityId
                it[lat] = latInput
                it[lng] = lngInput
                it[timestamp] = curTimestamp
            }
        }
    }

    override suspend fun getBirdById(birdId: UUID): BirdWithEvents? {
        val dbResults = dbQuery {
            (Birds innerJoin Events)
                .select {
                    (Birds.id eq EntityID(birdId, Birds)) and
                            (Events.bird_id eq Birds.id)
                }
                .mapNotNull{
                    it
                }
        }

        if(dbResults.isEmpty()) return null

        val eventArr : Array<Event> = dbResults.map{ toEvent(it) }.toTypedArray()
        return toBirdWithEvents(dbResults[0], eventArr)
    }

    override suspend fun getBirdsByState(state: String): Array<Bird> {
        require(state == "idle" || state == "in_ride") { "not a valid bird state!" }
        return dbQuery {
            Birds.select{ Birds.state eq state}.mapNotNull{toBird(it)}.toTypedArray()
        }
    }

    override suspend fun clearBothTables(){
        Birds.deleteAll()
        Events.deleteAll()
    }

    private fun toEvent(row: ResultRow): Event =
        Event(
            id = row[Events.id].value.toString(),
            bird_id = row[Events.bird_id].toString(),
            lat = row[Events.lat],
            lng = row[Events.lng],
            kind = row[Events.kind],
            timestamp = row[Events.timestamp])

    private fun toBird(row: ResultRow): Bird =
        Bird(
            id = row[Birds.id].value.toString(),
            lat = row[Birds.lat],
            lng = row[Birds.lng],
            state = row[Birds.state])

    private fun toBirdWithEvents(row: ResultRow, eventArr: Array<Event>): BirdWithEvents =
        BirdWithEvents(
            id = row[Birds.id].value.toString(),
            lat = row[Birds.lat],
            lng = row[Birds.lng],
            state = row[Birds.state],
            events = eventArr
        )
}