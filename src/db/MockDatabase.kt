package com.birdTakehome.db

import com.birdTakehome.model.*
import java.util.*
import javax.inject.*
import kotlin.collections.ArrayList

class MockDatabase @Inject constructor() : DB {
    private val events = ArrayList<Event>()
    private val birds = ArrayList<Bird>()
    val needToInitialize = false

    override suspend fun addEvent(kindInput: String, bird_idInput: String, latInput: Float, lngInput: Float, timestampInput: Long) {
        require(kindInput == "ride_start" || kindInput == "ride_end") { "not a valid event kind!" }
        // adding the new event
        events.add(
            Event(
                id = UUID.randomUUID().toString(),
                kind = kindInput,
                bird_id = bird_idInput,
                lat = latInput,
                lng = lngInput,
                timestamp = timestampInput
            )
        )

        val newState = if(kindInput == "ride_start") "in_ride" else "idle"
        val indexOfThisBird : Int = birds.indexOfFirst{  it.id == bird_idInput }
        if(indexOfThisBird ==  -1) { // INSERT: we haven't seen this bird before
            birds.add(
                Bird(
                    id = bird_idInput,
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

    override suspend fun getBirdById(bird_idInput: String): BirdWithEvents? {
        val bird = birds.find{it.id == bird_idInput} ?: return null
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