package com.trackmybus.theKeg.features.schedule.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class StopInSequenceDto(
    val stopId: String,
    val stopName: String,
    val stopLat: Double,
    val stopLon: Double,
    val sequence: Int,
)
