package com.trackmybus.theKeg.features.schedule.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing calendar dates data.
 *
 * The `CalendarDatesDto` class is used to transfer data about exceptions to the service IDs in the `calendar.txt` file,
 * with an explicit date and an exception type (e.g., Bank Holidays).
 * This data is typically sourced from the `calendar_dates.txt` file.
 *
 * @property serviceId The unique identifier for the service. This will differ from the incumbent system.
 * @property date The date of the exception in YYYYMMDD format.
 * @property exceptionType The type of exception (1 for added service, 2 for removed service).
 */
@Serializable
data class CalendarDateDto(
    @SerialName("service_id") val serviceId: String? = null,
    @SerialName("date") val date: Int? = null,
    @SerialName("exception_type") val exceptionType: Int? = null,
)
