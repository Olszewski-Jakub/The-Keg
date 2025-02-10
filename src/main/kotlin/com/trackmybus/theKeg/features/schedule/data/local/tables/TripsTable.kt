package com.trackmybus.theKeg.features.schedule.data.local.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

/**
 * Represents the `trips` table, which contains information about individual trips.
 */
object TripsTable : IdTable<String>("trips") {
    val routeId = reference("route_id", RoutesTable)
    val serviceId = reference("service_id", CalendarsTable).nullable()
    val tripId = varchar("trip_id", 255).entityId()
    val tripHeadsign = varchar("trip_headsign", 255).nullable()
    val tripShortName = varchar("trip_short_name", 255).nullable()
    val directionId = integer("direction_id").nullable()
    val blockId = varchar("block_id", 255).nullable()
    val shapeId = varchar("shape_id", 255).nullable()
    override val id: Column<EntityID<String>> = tripId
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(tripId)
}
