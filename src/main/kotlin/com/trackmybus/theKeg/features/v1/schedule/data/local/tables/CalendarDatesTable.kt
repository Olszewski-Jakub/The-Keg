package com.trackmybus.theKeg.features.v1.schedule.data.local.tables

import com.trackmybus.theKeg.features.v1.schedule.domain.model.ScheduleAdjustmentType
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

/**
 * Represents the `calendar_dates` table in the local database.
 * This table contains exceptions to the regular schedule specified in the `calendar` table.
 * Each row defines a service on a specific date with an exception type.
 */
object CalendarDatesTable : IntIdTable("calendar_dates") {
    val serviceId = reference("service_id", CalendarsTable).nullable()
    val date = date("date").nullable()
    val exceptionType = enumeration<ScheduleAdjustmentType>("exception_type").nullable()
}
