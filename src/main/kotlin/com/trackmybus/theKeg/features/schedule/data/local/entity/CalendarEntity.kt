package com.trackmybus.theKeg.features.schedule.data.local.entity

import com.trackmybus.theKeg.features.schedule.data.local.tables.CalendarsTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Entity class representing a calendar in the local database.
 * @param id The ID of the calendar.
 * @property serviceId The service ID associated with the calendar.
 * @property monday Indicates if the service runs on Monday.
 * @property tuesday Indicates if the service runs on Tuesday.
 * @property wednesday Indicates if the service runs on Wednesday.
 * @property thursday Indicates if the service runs on Thursday.
 * @property friday Indicates if the service runs on Friday.
 * @property saturday Indicates if the service runs on Saturday.
 * @property sunday Indicates if the service runs on Sunday.
 * @property startDate The start date of the service.
 * @property endDate The end date of the service.
 */
class CalendarEntity(
    id: EntityID<String>,
) : Entity<String>(id) {
    companion object : EntityClass<String, CalendarEntity>(CalendarsTable)

    var serviceId by CalendarsTable.serviceId
    var monday by CalendarsTable.monday
    var tuesday by CalendarsTable.tuesday
    var wednesday by CalendarsTable.wednesday
    var thursday by CalendarsTable.thursday
    var friday by CalendarsTable.friday
    var saturday by CalendarsTable.saturday
    var sunday by CalendarsTable.sunday
    var startDate by CalendarsTable.startDate
    var endDate by CalendarsTable.endDate
}
