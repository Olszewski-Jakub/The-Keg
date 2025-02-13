package com.trackmybus.theKeg.features.v1.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GTFSDto(
    val agencyDto: List<AgencyDto> = emptyList(),
    val calendarDto: List<CalendarDto> = emptyList(),
    val calendarDatesDto: List<CalendarDateDto> = emptyList(),
    val routesDto: List<RouteDto> = emptyList(),
    val shapesDto: List<ShapeDto> = emptyList(),
    val stopsDto: List<StopDto> = emptyList(),
    val stopTimesDto: List<StopTimeDto> = emptyList(),
    val tripsDto: List<TripDto> = emptyList(),
    val feedInfoDto: List<FeedInfoDto> = emptyList(),
)
