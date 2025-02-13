package com.trackmybus.theKeg.features.v1.domain.repository.gtfsQueryRepository

import com.trackmybus.theKeg.features.v1.data.remote.dto.DepartureTimeDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.RouteDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.StopInSequenceDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.StopOnTripDto
import com.trackmybus.theKeg.features.v1.domain.model.RouteStopInfo
import kotlinx.datetime.LocalDate

interface GtfsQueryRepository {
    suspend fun getStopsForGivenRoute(
        routeId: String,
        firstStopId: String,
        endStopId: String,
    ): Result<List<StopInSequenceDto>>

    suspend fun getAllDepartureTimesForStop(
        stopId: String,
        routeId: String,
        dateTime: LocalDate,
    ): Result<List<DepartureTimeDto>>

    suspend fun getStopsForTrip(tripId: String): Result<List<StopOnTripDto>>

    suspend fun getRoutesPassingThroughStop(stopId: String): Result<List<RouteDto>>

    suspend fun getFirstAndLastStopsForRoute(routeId: String): Result<List<RouteStopInfo>>
}
