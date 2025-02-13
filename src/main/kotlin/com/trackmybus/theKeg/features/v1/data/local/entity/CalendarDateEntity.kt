package com.trackmybus.theKeg.features.v1.data.local.entity

import com.trackmybus.theKeg.features.v1.data.local.tables.CalendarDatesTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Entity class representing a calendar date in the local database.
 * @param id The ID of the calendar date.
 * @property serviceId The service ID associated with the calendar date.
 * @property date The date of the calendar entry.
 * @property exceptionType The exception type for the calendar date.
 */
class CalendarDateEntity(
    id: EntityID<Int>,
) : IntEntity(id) {
    companion object : IntEntityClass<CalendarDateEntity>(CalendarDatesTable)

    var serviceId by CalendarDatesTable.serviceId
    var date by CalendarDatesTable.date
    var exceptionType by CalendarDatesTable.exceptionType
}
