package com.trackmybus.theKeg.features.v1.data.remote.dto

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class StopOnTripDto(
    val stopSequence: Int,
    val stopId: String,
    val stopName: String,
    val departureTime: LocalTime,
    val stopLat: Double,
    val stopLon: Double,
)
