package com.trackmybus.theKeg.features.schedule.data.local.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.time

/**
 * Represents the `stop_times` table, which contains scheduled arrival and departure times at stops.
 */
object StopTimesTable : IntIdTable("stop_times") {
    val tripId = reference("trip_id", TripsTable)
    val arrivalTime = time("arrival_time").nullable()
    val departureTime = time("departure_time").nullable()
    val stopId = reference("stop_id", StopsTable).nullable()
    val stopSequence = integer("stop_sequence").nullable()
    val stopHeadsign = varchar("stop_headsign", 255).nullable()
    val pickupType = integer("pickup_type").nullable()
    val dropOffType = integer("drop_off_type").nullable()
    val timepoint = integer("timepoint").nullable()
}