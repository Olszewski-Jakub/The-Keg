package com.trackmybus.theKeg.features.schedule.data.local.entity

import com.trackmybus.theKeg.features.schedule.data.local.tables.RoutesTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Entity class representing a route in the local database.
 * @param id The ID of the route.
 * @property routeId The ID of the route.
 * @property agencyId The ID of the agency associated with the route.
 * @property routeShortName The short name of the route.
 * @property routeLongName The long name of the route.
 * @property routeDescription The description of the route.
 * @property routeType The type of the route.
 * @property routeUrl The URL of the route.
 * @property routeColor The color of the route.
 * @property routeTextColor The text color of the route.
 */
class RouteEntity(
    id: EntityID<String>,
) : Entity<String>(id) {
    companion object : EntityClass<String, RouteEntity>(RoutesTable)

    var routeId by RoutesTable.routeId
    var agencyId by RoutesTable.agencyId
    var routeShortName by RoutesTable.routeShortName
    var routeLongName by RoutesTable.routeLongName
    var routeDescription by RoutesTable.routeDescription
    var routeType by RoutesTable.routeType
    var routeUrl by RoutesTable.routeUrl
    var routeColor by RoutesTable.routeColor
    var routeTextColor by RoutesTable.routeTextColor
}
