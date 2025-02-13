package com.trackmybus.theKeg.features.v1.domain.repository.gtfsQueryRepository

import com.trackmybus.theKeg.features.v1.data.local.manager.GtfsQueryManager
import com.trackmybus.theKeg.features.v1.data.remote.dto.DepartureTimeDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.RouteDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.StopInSequenceDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.StopOnTripDto
import com.trackmybus.theKeg.features.v1.domain.model.RouteStopInfo
import io.ktor.util.logging.Logger
import kotlinx.datetime.LocalDate

class GtfsQueryRepositoryImpl(
    private val logger: Logger,
    private val gtfsQueryManager: GtfsQueryManager,
) : GtfsQueryRepository {
    override suspend fun getStopsForGivenRoute(
        routeId: String,
        firstStopId: String,
        endStopId: String,
    ): Result<List<StopInSequenceDto>> = gtfsQueryManager.getStopsForGivenRoute(routeId, firstStopId, endStopId)

    override suspend fun getAllDepartureTimesForStop(
        stopId: String,
        routeId: String,
        dateTime: LocalDate,
    ): Result<List<DepartureTimeDto>> =
        gtfsQueryManager.getDepartureTimesForRouteAndStop(
            stopId = stopId,
            routeId = routeId,
            date = dateTime.toString(),
        )

    override suspend fun getStopsForTrip(tripId: String): Result<List<StopOnTripDto>> = gtfsQueryManager.getStopsForTrip(tripId)

    override suspend fun getRoutesPassingThroughStop(stopId: String): Result<List<RouteDto>> =
        gtfsQueryManager.getRoutesPassingThroughStop(stopId)

    override suspend fun getFirstAndLastStopsForRoute(routeId: String): Result<List<RouteStopInfo>> =
        gtfsQueryManager.getFirstAndLastStopsForRoute(routeId = routeId)
}
