package com.trackmybus.theKeg.features.v1.domain.model

data class RouteStopInfo(
    val firstStopId: String,
    val firstStopName: String,
    val lastStopId: String,
    val lastStopName: String,
    val directionId: Int,
)
