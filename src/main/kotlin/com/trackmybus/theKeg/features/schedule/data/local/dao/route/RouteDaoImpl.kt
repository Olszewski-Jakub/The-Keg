package com.trackmybus.theKeg.features.schedule.data.local.dao.route

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.entity.RouteEntity
import com.trackmybus.theKeg.features.schedule.data.local.tables.AgenciesTable
import com.trackmybus.theKeg.features.schedule.data.local.tables.RoutesTable
import com.trackmybus.theKeg.features.schedule.domain.model.Route
import io.ktor.util.logging.Logger
import org.jetbrains.exposed.dao.id.EntityID

class RouteDaoImpl(
    private val logger: Logger,
    private val dbFactory: DatabaseFactory,
) : RouteDao {
    override suspend fun getAllRoutes(): Result<List<RouteEntity>> =
        runCatching {
            dbFactory.dbQuery {
                RouteEntity.all().toList()
            }
        }.onFailure {
            logger.error("Error getting all routes", it)
        }

    override suspend fun getRouteById(id: String): Result<RouteEntity?> =
        runCatching {
            dbFactory.dbQuery {
                RouteEntity.findById(id)
            }
        }.onFailure {
            logger.error("Error getting route by id: $id", it)
        }

    override suspend fun addRoute(route: Route): Result<RouteEntity> =
        runCatching {
            dbFactory.dbQuery {
                RouteEntity.new {
                    setPropertiesFrom(route)
                }
            }
        }.onFailure {
            logger.error("Error adding route: $route", it)
        }

    override suspend fun updateRoute(route: Route): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                val existingRoute = RouteEntity.findById(route.routeId)
                if (existingRoute != null) {
                    existingRoute.apply {
                        setPropertiesFrom(route)
                    }
                    true
                } else {
                    throw IllegalArgumentException("Route with ID ${route.routeId} does not exist")
                }
            }
        }.onFailure {
            logger.error("Error updating route: $route", it)
        }

    override suspend fun deleteRoute(id: String): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                RouteEntity.findById(id)?.delete() != null
            }
        }.onFailure {
            logger.error("Error deleting route by id: $id", it)
        }

    private fun RouteEntity.setPropertiesFrom(route: Route) {
        this.routeId = EntityID<String>(route.routeId, RoutesTable)
        this.agencyId = EntityID(route.agencyId, AgenciesTable)
        this.routeShortName = route.routeShortName
        this.routeLongName = route.routeLongName
        this.routeType = route.routeType
        this.routeColor = route.routeColor
        this.routeTextColor = route.routeTextColor
        this.routeDescription = route.routeDescription
        this.routeUrl = route.routeUrl
    }
}
