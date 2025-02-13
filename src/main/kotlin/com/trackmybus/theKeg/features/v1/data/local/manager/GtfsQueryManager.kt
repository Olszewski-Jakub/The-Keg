package com.trackmybus.theKeg.features.v1.data.local.manager

import com.trackmybus.theKeg.features.v1.data.remote.dto.DepartureTimeDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.RouteDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.StopInSequenceDto
import com.trackmybus.theKeg.features.v1.data.remote.dto.StopOnTripDto
import com.trackmybus.theKeg.features.v1.domain.model.RouteStopInfo

/**
 * Interface for managing GTFS (General Transit Feed Specification) queries.
 */
interface GtfsQueryManager {
    /**
     * Retrieves the stops for a given route.
     *
     * @param routeId The ID of the route.
     * @param firstStopId The ID of the first stop.
     * @param endStopId The ID of the end stop.
     * @return A [Result] containing a list of [StopInSequenceDto] objects.
     */
    suspend fun getStopsForGivenRoute(
        routeId: String,
        firstStopId: String,
        endStopId: String,
    ): Result<List<StopInSequenceDto>>

    /**
     * Retrieves the departure times for a given route and stop on a specific date.
     *
     * @param date The date for which to retrieve departure times.
     * @param routeId The ID of the route.
     * @param stopId The ID of the stop.
     * @return A [Result] containing a list of [DepartureTimeDto] objects.
     */
    suspend fun getDepartureTimesForRouteAndStop(
        date: String,
        routeId: String,
        stopId: String,
    ): Result<List<DepartureTimeDto>>

    /**
     * Retrieves the stops for a given trip.
     *
     * @param tripId The ID of the trip.
     * @return A [Result] containing a list of [StopOnTripDto] objects.
     */
    suspend fun getStopsForTrip(tripId: String): Result<List<StopOnTripDto>>

    /**
     * Retrieves the routes passing through a given stop.
     *
     * @param stopId The ID of the stop.
     * @return A [Result] containing a list of [RouteDto] objects.
     */
    suspend fun getRoutesPassingThroughStop(stopId: String): Result<List<RouteDto>>

    /**
     * Retrieves the first and last stops for a given route.
     *
     * @param routeId The ID of the route.
     * @return A [Result] containing a list of [RouteStopInfo] objects.
     */
    suspend fun getFirstAndLastStopsForRoute(routeId: String): Result<List<RouteStopInfo>>
}
