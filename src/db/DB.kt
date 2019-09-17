package com.birdTakehome.db

import com.birdTakehome.model.*
import java.util.*

interface DB {
    suspend fun addEvent(kindInput: String, bird_idInput: UUID, latInput: Float, lngInput: Float)
    suspend fun getBirdById(birdId: UUID): BirdWithEvents?
    suspend fun getBirdsByState(state: String): Array<Bird>
    suspend fun clearBothTables()
}