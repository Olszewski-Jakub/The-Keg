package com.trackmybus.theKeg.features.schedule.domain.model

data class RouteStopInfo(
    val firstStopId: String,
    val firstStopName: String,
    val lastStopId: String,
    val lastStopName: String,
    val directionId: Int,
)
