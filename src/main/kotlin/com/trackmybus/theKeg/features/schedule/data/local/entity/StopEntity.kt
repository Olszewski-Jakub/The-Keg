package com.trackmybus.theKeg.features.schedule.data.local.entity

import com.trackmybus.theKeg.features.schedule.data.local.tables.StopsTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Entity class representing a stop in the local database.
 * @param id The ID of the stop.
 * @property stopId The ID of the stop.
 * @property stopCode The code of the stop.
 * @property stopName The name of the stop.
 * @property stopDesc The description of the stop.
 * @property stopLat The latitude of the stop.
 * @property stopLon The longitude of the stop.
 * @property zoneId The zone ID of the stop.
 * @property stopUrl The URL of the stop.
 * @property locationType The location type of the stop.
 * @property parentStation The parent station of the stop.
 */
class StopEntity(id: EntityID<String>): Entity<String>(id) {
    companion object : EntityClass<String, StopEntity>(StopsTable)

    var stopId by StopsTable.stopId
    var stopCode by StopsTable.stopCode
    var stopName by StopsTable.stopName
    var stopDesc by StopsTable.stopDesc
    var stopLat by StopsTable.stopLat
    var stopLon by StopsTable.stopLon
    var zoneId by StopsTable.zoneId
    var stopUrl by StopsTable.stopUrl
    var locationType by StopsTable.locationType
    var parentStation by StopsTable.parentStation
}