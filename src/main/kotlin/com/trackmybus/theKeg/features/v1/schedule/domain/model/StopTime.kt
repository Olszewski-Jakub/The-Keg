package com.trackmybus.theKeg.features.v1.schedule.domain.model

import kotlinx.datetime.LocalTime

data class StopTime(
    val id: Int,
    val tripId: String,
    val arrivalTime: LocalTime,
    val departureTime: LocalTime,
    val stopId: String,
    val stopSequence: Int,
    val stopHeadsign: String,
    val pickupType: Int,
    val dropOffType: Int,
    val timepoint: Int,
)
