package com.trackmybus.theKeg.features.v1.domain.model

data class Stop(
    val stopId: String,
    val stopCode: Int,
    val stopName: String,
    val stopLat: Double,
    val stopLon: Double,
    val stopDesc: String,
    val zoneId: String,
    val stopUrl: String,
    val locationType: Int?,
    val parentStation: String,
)
