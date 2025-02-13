package com.trackmybus.theKeg.features.v1.schedule.data.local.dao.route

import com.trackmybus.theKeg.features.v1.schedule.data.local.entity.RouteEntity
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Route

/**
 * Data Access Object (DAO) interface for managing route data in the local database.
 */
interface RouteDao {
    /**
     * Retrieves all routes from the local database.
     * @return A [Result] containing a list of [RouteEntity] objects.
     */
    suspend fun getAllRoutes(): Result<List<RouteEntity>>

    /**
     * Retrieves a route by its ID.
     * @param id The ID of the route to retrieve.
     * @return A [Result] containing the [RouteEntity] object if found, or null if not found.
     */
    suspend fun getRouteById(id: String): Result<RouteEntity?>

    suspend fun getRoutesByType(type: Int): Result<List<RouteEntity>>

    /**
     * Adds a new route to the local database.
     * @param route The [RouteEntity] object to add.
     * @return A [Result] containing the newly added [RouteEntity] object.
     */
    suspend fun addRoute(route: Route): Result<RouteEntity>

    /**
     * Updates an existing route in the local database.
     * @param route The [RouteEntity] object containing updated information.
     * @return A [Result] indicating whether the update was successful.
     */
    suspend fun updateRoute(route: Route): Result<Boolean>

    /**
     * Deletes a route from the local database by its ID.
     * @param id The ID of the route to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteRoute(id: String): Result<Boolean>
}
