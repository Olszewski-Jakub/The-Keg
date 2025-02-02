package com.trackmybus.theKeg.features.schedule.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing stop data.
 *
 * The `StopsDto` class is used to transfer data about stops referenced in GTFS.
 * This data is typically sourced from the `stops.txt` file.
 *
 * @property stopId The unique identifier for the stop.
 * @property stopCode The PlateCode of the stop, if it exists (new field).
 * @property stopName The name of the stop, standardized to use the ShortCommonName from NaPTAN.
 * @property stopDesc The description of the stop.
 * @property stopLat The latitude of the stop.
 * @property stopLon The longitude of the stop.
 * @property zoneId The zone identifier for the stop.
 * @property stopUrl The URL of the stop's website.
 * @property locationType The type of the location.
 * @property parentStation The identifier of the parent station.
 */
@Serializable
data class StopDto(
    @SerialName("stop_id") val stopId: String? = null,
    @SerialName("stop_code") val stopCode: Int? = null,
    @SerialName("stop_name") val stopName: String? = null,
    @SerialName("stop_desc") val stopDesc: String? = null,
    @SerialName("stop_lat") val stopLat: Double? = null,
    @SerialName("stop_lon") val stopLon: Double? = null,
    @SerialName("zone_id") val zoneId: String? = null,
    @SerialName("stop_url") val stopUrl: String? = null,
    @SerialName("location_type") val locationType: Int? = null,
    @SerialName("parent_station") val parentStation: String? = null,
)
