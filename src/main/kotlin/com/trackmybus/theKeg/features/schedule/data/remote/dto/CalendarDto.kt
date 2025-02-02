package com.trackmybus.theKeg.features.schedule.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing calendar data.
 *
 * The `CalendarDto` class is used to transfer data about the days a particular trip runs on,
 * as well as the timeframe it runs in. This data is typically sourced from the `calendar.txt` file.
 *
 * @property serviceId The unique identifier for the service, which serves as a lookup reference to `service_id` in [TripDto].
 * @property monday Indicates if the service runs on Monday (1 if it runs, 0 if it does not).
 * @property tuesday Indicates if the service runs on Tuesday (1 if it runs, 0 if it does not).
 * @property wednesday Indicates if the service runs on Wednesday (1 if it runs, 0 if it does not).
 * @property thursday Indicates if the service runs on Thursday (1 if it runs, 0 if it does not).
 * @property friday Indicates if the service runs on Friday (1 if it runs, 0 if it does not).
 * @property saturday Indicates if the service runs on Saturday (1 if it runs, 0 if it does not).
 * @property sunday Indicates if the service runs on Sunday (1 if it runs, 0 if it does not).
 * @property startDate The start date of the service in YYYYMMDD format.
 * @property endDate The end date of the service in YYYYMMDD format.
 */
@Serializable
data class CalendarDto(
    @SerialName("service_id") val serviceId: String? = null,
    @SerialName("monday") val monday: Boolean? = null,
    @SerialName("tuesday") val tuesday: Boolean? = null,
    @SerialName("wednesday") val wednesday: Boolean? = null,
    @SerialName("thursday") val thursday: Boolean? = null,
    @SerialName("friday") val friday: Boolean? = null,
    @SerialName("saturday") val saturday: Boolean? = null,
    @SerialName("sunday") val sunday: Boolean? = null,
    @SerialName("start_date") val startDate: Int? = null,
    @SerialName("end_date") val endDate: Int? = null,
)
