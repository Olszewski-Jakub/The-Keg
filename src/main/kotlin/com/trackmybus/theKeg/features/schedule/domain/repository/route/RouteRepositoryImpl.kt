package com.trackmybus.theKeg.features.schedule.domain.repository.route

import com.trackmybus.theKeg.features.schedule.data.local.dao.route.RouteDao
import com.trackmybus.theKeg.features.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.schedule.domain.model.Route
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class RouteRepositoryImpl(
    private val logger: Logger,
    private val routeDao: RouteDao,
) : RouteRepository {
    override suspend fun getAll(): Result<List<Route>> {
        logger.info("Fetching all routes")
        return routeDao
            .getAllRoutes()
            .mapResult { it.map { routeEntity -> routeEntity.toModel() } }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched all routes") }
                result.onFailure { logger.error("Error fetching all routes", it) }
            }
    }

    override suspend fun getById(id: String): Result<Route?> {
        logger.info("Fetching route with id: $id")
        return routeDao
            .getRouteById(id)
            .mapResult { it?.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched route with id: $id") }
                result.onFailure { logger.error("Error fetching route with id: $id", it) }
            }
    }

    override suspend fun add(calendar: Route): Result<Route> {
        logger.info("Adding route: ${calendar.routeId}")
        return routeDao
            .addRoute(calendar)
            .mapResult { it.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully added route: ${calendar.routeId}") }
                result.onFailure { logger.error("Error adding route: ${calendar.routeId}", it) }
            }
    }

    override suspend fun update(calendar: Route): Result<Boolean> {
        logger.info("Updating route: ${calendar.routeId}")
        return routeDao
            .updateRoute(calendar)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully updated route: ${calendar.routeId}") }
                result.onFailure { logger.error("Error updating route: ${calendar.routeId}", it) }
            }
    }

    override suspend fun deleteById(id: String): Result<Boolean> {
        logger.info("Deleting route with id: $id")
        return routeDao
            .deleteRoute(id)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully deleted route with id: $id") }
                result.onFailure { logger.error("Error deleting route with id: $id", it) }
            }
    }
}
