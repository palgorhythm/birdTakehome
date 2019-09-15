package com.birdTakehome.repository

import com.birdTakehome.model.*

interface Repository {
    suspend fun addEvent(event: Event)
    suspend fun getBirdById(birdId: String): Bird?
    suspend fun getBirdsByState(state: String): List<Bird>?
}