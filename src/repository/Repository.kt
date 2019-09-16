package com.birdTakehome.repository

import com.birdTakehome.model.*
import java.util.*

interface Repository {
    suspend fun addEvent(kindInput: String, bird_idInput: UUID, latInput: Float, lngInput: Float)
    suspend fun getBirdById(birdId: UUID): BirdWithEvents?
    suspend fun getBirdsByState(state: String): Array<Bird>
    suspend fun clearBothTables()
}