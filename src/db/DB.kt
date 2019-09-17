package com.birdTakehome.db

import com.birdTakehome.model.*

interface DB {
    suspend fun addEvent(kindInput: String, bird_idInput: String, latInput: Float, lngInput: Float, timestampInput: Long)
    suspend fun getBirdById(bird_idInput: String): BirdWithEvents?
    suspend fun getBirdsByState(state: String): Array<Bird>
    suspend fun clearBothTables()
}