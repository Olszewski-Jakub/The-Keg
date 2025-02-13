package com.trackmybus.theKeg.features.v1.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RouteStopInfoDto(
    val firstStopId: String,
    val firstStopName: String,
    val lastStopId: String,
    val lastStopName: String,
    val directionId: Int,
)
