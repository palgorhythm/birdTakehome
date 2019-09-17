package com.birdTakehome.db

import com.birdTakehome.model.*
import com.birdTakehome.db.DatabaseInitializer.dbQuery
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.*
import java.util.*
import javax.inject.*

class EventsBirdsDatabase @Inject constructor(): DB {
    val needToInitialize = true
    override suspend fun addEvent(kindInput: String, bird_idInput: String, latInput: Float, lngInput: Float, timestampInput: Long){
        require(kindInput == "ride_start" || kindInput == "ride_end") { "not a valid event kind!" } //error handling
        transaction {
            val birdEntityId = EntityID(UUID.fromString(bird_idInput), Birds)
            // convert the string input into a UUID (if applicable) and then into entityid for insertion into the table
            Birds.insertOrUpdate(Birds.id){ // upsert
                it[id] = birdEntityId
                it[lat] = latInput
                it[lng] = lngInput
                it[state] = if(kindInput == "ride_start") "in_ride" else "idle"
            }
            Events.insert{ // insert the new event
                it[kind] = kindInput
                it[bird_id] = birdEntityId
                it[lat] = latInput
                it[lng] = lngInput
                it[timestamp] = timestampInput
            }
        }
    }

    override suspend fun getBirdById(bird_idInput: String): BirdWithEvents? {
        val dbResults = dbQuery {
            (Birds innerJoin Events)  // get all of the events for this bird
                .select {
                    (Birds.id eq EntityID(UUID.fromString(bird_idInput), Birds)) and
                            (Events.bird_id eq Birds.id)
                }
                .mapNotNull{
                    it
                }
        }

        if(dbResults.isEmpty()) return null

        val eventArr : Array<Event> = dbResults.map{ toEvent(it) }.toTypedArray() // build an array of events from the result rows
        return toBirdWithEvents(dbResults[0], eventArr)
        // first arg is a result row containing the bird info (all of them do)
    }

    override suspend fun getBirdsByState(state: String): Array<Bird> {
        require(state == "idle" || state == "in_ride") { "not a valid bird state!" } // error handling
        return dbQuery {
            Birds.select{ Birds.state eq state}.mapNotNull{toBird(it)}.toTypedArray() // db query and convert result rows to instances of Bird data class
        }
    }

    override suspend fun clearBothTables(){
        Birds.deleteAll() // just in case we need this
        Events.deleteAll()
    }

    private fun toEvent(row: ResultRow): Event = // utility function to build instances of Event data class from db query results
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