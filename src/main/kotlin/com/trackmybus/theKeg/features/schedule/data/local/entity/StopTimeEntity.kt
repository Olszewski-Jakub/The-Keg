package com.trackmybus.theKeg.features.schedule.data.local.entity

import com.trackmybus.theKeg.features.schedule.data.local.tables.StopTimesTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Entity class representing a stop time in the local database.
 * @param id The ID of the stop time.
 * @property tripId The ID of the trip associated with the stop time.
 * @property arrivalTime The arrival time at the stop.
 * @property departureTime The departure time from the stop.
 * @property stopId The ID of the stop.
 * @property stopSequence The sequence of the stop within the trip.
 * @property stopHeadsign The headsign of the stop.
 * @property pickupType The pickup type at the stop.
 * @property dropOffType The drop-off type at the stop.
 * @property timepoint Indicates if the stop time is a timepoint.
 */
class StopTimeEntity(
    id: EntityID<Int>,
) : IntEntity(id) {
    companion object : IntEntityClass<StopTimeEntity>(StopTimesTable)

    var tripId by StopTimesTable.tripId
    var arrivalTime by StopTimesTable.arrivalTime
    var departureTime by StopTimesTable.departureTime
    var stopId by StopTimesTable.stopId
    var stopSequence by StopTimesTable.stopSequence
    var stopHeadsign by StopTimesTable.stopHeadsign
    var pickupType by StopTimesTable.pickupType
    var dropOffType by StopTimesTable.dropOffType
    var timepoint by StopTimesTable.timepoint
}
