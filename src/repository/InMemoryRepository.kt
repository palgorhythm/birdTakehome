package com.birdTakehome.repository

import com.birdTakehome.model.*
import java.lang.IllegalArgumentException
import java.sql.*
import java.util.concurrent.atomic.*

class InMemoryRepository : Repository{
    private val idCounter = AtomicInteger()
    private val events = ArrayList<Event>()
    private val birds = ArrayList<Bird>()

    override suspend fun addEvent(event: Event): Event {
        // adding the new event
        event.id = idCounter.incrementAndGet()
        event.timestamp = Timestamp(System.currentTimeMillis()).time
        events.add(event)

        // modifying a bird entry
        var newState : String
        if(event.kind == "ride_start"){
            newState = "in_ride"
        } else if (event.kind == "ride_end"){
            newState = "idle"
        } else {
            throw IllegalArgumentException("incorrect event kind!")
        }
        var indexOfThisBird : Int = birds.indexOfFirst{  it.id == event.bird_id }
        if(indexOfThisBird ==  -1) { // this event is for a bird we don't know about yet
            birds.add(Bird(event.bird_id, event.lat, event.lng, newState))
        } else { // this event is for a bird we know exists
            birds[indexOfThisBird].lat = event.lat
            birds[indexOfThisBird].lng = event.lng
            birds[indexOfThisBird].state = newState
        }
        println(birds)
        return event
    }

    override suspend fun getBirdById(birdId: String): Bird? {
        val bird = birds.find{it.id == birdId}
        val eventsForThisBird = events.filter{it.bird_id == bird?.id}
        bird?.events = eventsForThisBird
        return bird
    }

    override suspend fun getBirdsByState(state: String): List<Bird>? {
        return birds.filter{it.state == state}
    }
}