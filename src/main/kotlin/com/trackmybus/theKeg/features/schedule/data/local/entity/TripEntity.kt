package com.trackmybus.theKeg.features.schedule.data.local.entity

import com.trackmybus.theKeg.features.schedule.data.local.tables.TripsTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Entity class representing a trip in the local database.
 * @param id The ID of the trip.
 * @property routeId The ID of the route associated with the trip.
 * @property serviceId The ID of the service associated with the trip.
 * @property tripId The ID of the trip.
 * @property tripHeadsign The headsign of the trip.
 * @property tripShortName The short name of the trip.
 * @property directionId The direction ID of the trip.
 * @property blockId The block ID of the trip.
 * @property shapeId The shape ID of the trip.
 */
class TripEntity(id: EntityID<String>): Entity<String>(id) {
    companion object : EntityClass<String, TripEntity>(TripsTable)

    var routeId by TripsTable.routeId
    var serviceId by TripsTable.serviceId
    var tripId by TripsTable.tripId
    var tripHeadsign by TripsTable.tripHeadsign
    var tripShortName by TripsTable.tripShortName
    var directionId by TripsTable.directionId
    var blockId by TripsTable.blockId
    var shapeId by TripsTable.shapeId
}