package com.birdTakehome.model

data class Event(val kind: String, val bird_id: String, val lat: Float, val lng: Float){
    var id : Int? = null
    var timestamp: Long? = null
}
