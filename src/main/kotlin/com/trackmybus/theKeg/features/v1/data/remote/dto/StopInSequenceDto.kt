package com.trackmybus.theKeg.features.v1.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class StopInSequenceDto(
    val stopId: String,
    val stopName: String,
    val stopLat: Double,
    val stopLon: Double,
    val sequence: Int,
)
