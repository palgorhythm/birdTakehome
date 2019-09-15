package com.birdTakehome.model

import java.io.Serializable

data class Bird(val id: String, var lat: Float, var lng: Float, var state: String) : Serializable {
    var events: List<Event>? = null
}