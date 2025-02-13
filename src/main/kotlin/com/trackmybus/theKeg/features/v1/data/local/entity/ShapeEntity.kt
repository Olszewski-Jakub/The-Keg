package com.trackmybus.theKeg.features.v1.data.local.entity

import com.trackmybus.theKeg.features.v1.data.local.tables.ShapesTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Entity class representing a shape in the local database.
 * @param id The ID of the shape.
 * @property shapeId The ID of the shape.
 * @property shapePtLat The latitude of the shape point.
 * @property shapePtLon The longitude of the shape point.
 * @property shapePtSequence The sequence of the shape point.
 * @property shapeDistTraveled The distance traveled for the shape point.
 */
class ShapeEntity(
    id: EntityID<Int>,
) : IntEntity(id) {
    companion object : IntEntityClass<ShapeEntity>(ShapesTable)

    var shapeId by ShapesTable.shapeId
    var shapePtLat by ShapesTable.shapePtLat
    var shapePtLon by ShapesTable.shapePtLon
    var shapePtSequence by ShapesTable.shapePtSequence
    var shapeDistTraveled by ShapesTable.shapeDistTraveled
}
