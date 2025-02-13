package com.trackmybus.theKeg.features.v1.data.local.tables

import org.jetbrains.exposed.dao.id.IdTable

/**
 * Represents the `stops` table, which contains information about transit stops.
 */
object StopsTable : IdTable<String>("stops") {
    val stopId = varchar("stop_id", 255).entityId()
    val stopCode = integer("stop_code").nullable()
    val stopName = varchar("stop_name", 255).nullable()
    val stopDesc = varchar("stop_desc", 255).nullable()
    val stopLat = double("stop_lat").nullable()
    val stopLon = double("stop_lon").nullable()
    val zoneId = varchar("zone_id", 255).nullable()
    val stopUrl = varchar("stop_url", 255).nullable()
    val locationType = integer("location_type").nullable()
    val parentStation = varchar("parent_station", 255).nullable()
    override val id = stopId
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(stopId)
}
