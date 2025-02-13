package com.trackmybus.theKeg.features.v1.schedule.domain.model

data class Route(
    val routeId: String,
    val agencyId: String,
    val routeShortName: String,
    val routeLongName: String,
    val routeType: Int,
    val routeDescription: String,
    val routeUrl: String,
    val routeColor: String,
    val routeTextColor: String,
)
