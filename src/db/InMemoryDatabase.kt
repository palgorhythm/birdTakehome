package com.birdTakehome.db

import com.birdTakehome.model.*
import java.sql.*
import java.util.*
import javax.inject.*
import kotlin.collections.ArrayList

class InMemoryDatabase @Inject constructor() : DB {
    private val events = ArrayList<Event>()
    private val birds = ArrayList<Bird>()

    override suspend fun addEvent(kindInput: String, bird_idInput: UUID, latInput: Float, lngInput: Float) {
        require(kindInput == "ride_start" || kindInput == "ride_end") { "not a valid event kind!" }
        // adding the new event
        events.add(
            Event(
                id = UUID.randomUUID().toString(),
                kind = kindInput,
                bird_id = bird_idInput.toString(),
                lat = latInput,
                lng = lngInput,
                timestamp = Timestamp(System.currentTimeMillis()).time
            )
        )

        val newState = if(kindInput == "ride_start") "in_ride" else "idle"
        val indexOfThisBird : Int = birds.indexOfFirst{  it.id == bird_idInput.toString() }
        if(indexOfThisBird ==  -1) { // INSERT: we haven't seen this bird before
            birds.add(
                Bird(
                    id = bird_idInput.toString(),
                    lat = latInput,
                    lng = lngInput,
                    state = newState
                )
            )
        } else { // UPDATE: this bird is already registered
            birds[indexOfThisBird].lat = latInput
            birds[indexOfThisBird].lng = lngInput
            birds[indexOfThisBird].state = newState
        }
        println("here are the events")
        println(events)
    }

    override suspend fun getBirdById(birdId: UUID): BirdWithEvents? {
        val bird = birds.find{it.id == birdId.toString()} ?: return null
        val eventsForThisBird = events.filter{it.bird_id == bird.id}
        return BirdWithEvents(
            id = bird.id,
            lat = bird.lat,
            lng = bird.lng,
            state = bird.state,
            events = eventsForThisBird.toTypedArray()
        )
    }

    override suspend fun getBirdsByState(state: String): Array<Bird> {
        require(state == "idle" || state == "in_ride") { "not a valid bird state!" }
        return birds.filter{it.state == state}.toTypedArray()
    }

    override suspend fun clearBothTables(){
        birds.clear()
        events.clear()
    }

}