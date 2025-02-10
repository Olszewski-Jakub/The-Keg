package com.trackmybus.theKeg.features.schedule.data.remote.dto

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class DepartureTimeDto(
    val tripId: String,
    val departureTime: LocalTime,
    val directionId: Int,
)
