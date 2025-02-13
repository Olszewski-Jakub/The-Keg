package com.trackmybus.theKeg.features.v1.schedule.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing route data.
 *
 * The `RoutesDto` class is used to transfer data about routes.
 * This data is typically sourced from the `routes.txt` file.
 *
 * @property routeId The unique identifier for the route, which will change to a numeric format and will not be persistent.
 * @property agencyId The unique identifier for the agency, linking to [AgencyDto].
 * @property routeShortName The short name of the route, referring to the route number (e.g., 101).
 * @property routeLongName The long name of the route.
 * @property routeDesc The description of the route, which will change.
 * @property routeType The type of the route.
 * @property routeUrl The URL of the route's website (new field).
 * @property routeColor The color of the route (new field).
 * @property routeTextColor The text color of the route (new field).
 */
@Serializable
data class RouteDto(
    @SerialName("route_id") val routeId: String?,
    @SerialName("agency_id") val agencyId: String? = null,
    @SerialName("route_short_name") val routeShortName: String? = null,
    @SerialName("route_long_name") val routeLongName: String? = null,
    @SerialName("route_desc") val routeDesc: String? = null,
    @SerialName("route_type") val routeType: Int? = null,
    @SerialName("route_url") val routeUrl: String? = null,
    @SerialName("route_color") val routeColor: String? = null,
    @SerialName("route_text_color") val routeTextColor: String? = null,
)
