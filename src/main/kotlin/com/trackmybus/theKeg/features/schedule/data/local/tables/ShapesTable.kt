package com.trackmybus.theKeg.features.schedule.data.local.tables

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Represents the `shapes` table, which defines geographic shapes for routes.
 */
object ShapesTable : IntIdTable("shapes") {
    val shapeId = varchar("shape_id", 255)
    val shapePtLat = double("shape_pt_lat").nullable()
    val shapePtLon = double("shape_pt_lon").nullable()
    val shapePtSequence = integer("shape_pt_sequence").nullable()
    val shapeDistTraveled = double("shape_dist_traveled").nullable()
}
