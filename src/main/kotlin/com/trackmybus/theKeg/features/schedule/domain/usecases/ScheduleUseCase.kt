package com.trackmybus.theKeg.features.schedule.domain.usecases

import com.trackmybus.theKeg.features.schedule.data.remote.dto.DepartureTimeDto
import com.trackmybus.theKeg.features.schedule.data.remote.dto.RouteDto
import com.trackmybus.theKeg.features.schedule.data.remote.dto.RouteStopInfoDto
import com.trackmybus.theKeg.features.schedule.data.remote.dto.StopInSequenceDto
import com.trackmybus.theKeg.features.schedule.data.remote.dto.StopOnTripDto
import com.trackmybus.theKeg.features.schedule.resource.RouteType
import kotlinx.datetime.LocalDate

interface ScheduleUseCase {
    suspend fun fetchAndSaveGtfsData(): Result<Unit>

    suspend fun getRouteDirections(routeId: String): Result<List<RouteStopInfoDto>>

    suspend fun getStopsForGivenRoute(
        routeId: String,
        firstStopId: String,
        endStopId: String,
    ): Result<List<StopInSequenceDto>>

    suspend fun getAllRoutes(): Result<List<RouteDto>>

    suspend fun getRouteByType(type: RouteType): Result<List<RouteDto>>

    suspend fun getAllDepartureTimesForStop(
        stopId: String,
        routeId: String,
        dateTime: LocalDate,
    ): Result<List<DepartureTimeDto>>

    suspend fun getStopsForTrip(tripId: String): Result<List<StopOnTripDto>>

    suspend fun getRoutesPassingThroughStop(stopId: String): Result<List<RouteDto>>
}
