package com.trackmybus.theKeg.features.v1.data.local.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

/**
 * Represents the `routes` table, which contains information about transit routes.
 */
object RoutesTable : IdTable<String>("routes") {
    val routeId = varchar("route_id", 255).entityId()
    val agencyId = reference("agency_id", AgenciesTable).nullable()
    val routeShortName = varchar("route_short_name", 255).nullable()
    val routeLongName = varchar("route_long_name", 255).nullable()
    val routeDescription = varchar("route_desccription", 255).nullable()
    val routeType = integer("route_type").nullable()
    val routeUrl = varchar("route_url", 255).nullable()
    val routeColor = varchar("route_color", 255).nullable()
    val routeTextColor = varchar("route_text_color", 255).nullable()

    override val id: Column<EntityID<String>> = routeId
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(routeId)
}
