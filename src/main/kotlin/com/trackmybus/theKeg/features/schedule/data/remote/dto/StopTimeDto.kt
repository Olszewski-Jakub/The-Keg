package com.trackmybus.theKeg.features.schedule.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing stop times data.
 *
 * The `StopTimesDto` class is used to transfer data about the stops on a particular trip and the expected arrival/departure times at each stop.
 * This data is typically sourced from the `stop_times.txt` file.
 *
 * @property tripId The unique identifier for the trip, which serves as a lookup reference to [TripDto].
 * @property stopId The unique identifier for the stop, which serves as a lookup reference to [StopDto].
 * @property arrivalTime The expected arrival time at the stop.
 * @property departureTime The expected departure time from the stop.
 * @property stopSequence The sequence in which the stops are visited on the trip.
 * @property stopHeadsign The headsign for the stop, populated with the first stop on the trip.
 */
@Serializable
data class StopTimeDto(
    @SerialName("trip_id") val tripId: String? = null,
    @SerialName("arrival_time") val arrivalTime: String? = null,
    @SerialName("departure_time") val departureTime: String? = null,
    @SerialName("stop_id") val stopId: String? = null,
    @SerialName("stop_sequence") val stopSequence: Int? = null,
    @SerialName("stop_headsign") val stopHeadsign: String? = null,
    @SerialName("pickup_type") val pickupType: Int? = null,
    @SerialName("drop_off_type") val dropOffType: Int? = null,
    @SerialName("timepoint") val timepoint: Int? = null
)