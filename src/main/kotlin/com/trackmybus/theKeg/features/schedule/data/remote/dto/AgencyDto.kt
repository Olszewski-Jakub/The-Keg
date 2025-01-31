package com.trackmybus.theKeg.features.schedule.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing an agency.
 *
 * The `AgencyDto` class is used to transfer data about transport operators.
 * This data is typically sourced from the `agency.txt` file, which describes
 * the transport operators.
 *
 * @property agencyId The unique identifier for the agency.
 * @property agencyName The name of the agency.
 * @property agencyUrl The URL of the agency's website.
 * @property agencyTimezone The timezone in which the agency operates.
 */
@Serializable
data class AgencyDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("agency_id") val agencyId: String? = null,
    @SerialName("agency_name") val agencyName: String? = null,
    @SerialName("agency_url") val agencyUrl: String? = null,
    @SerialName("agency_timezone") val agencyTimezone: String? = null
)