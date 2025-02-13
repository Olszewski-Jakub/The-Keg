package com.trackmybus.theKeg.features.v1.data.local.dao.calendarDate

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.data.local.entity.CalendarDateEntity
import com.trackmybus.theKeg.features.v1.data.local.tables.CalendarsTable
import com.trackmybus.theKeg.features.v1.domain.model.CalendarDate
import com.trackmybus.theKeg.features.v1.domain.model.ScheduleAdjustmentType
import io.ktor.util.logging.Logger
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.dao.id.EntityID

class CalendarDatesDaoImpl(
    private val logger: Logger,
    private val dbFactory: DatabaseFactory,
) : CalendarDatesDao {
    override suspend fun getAllCalendarDates(): Result<List<CalendarDateEntity>> =
        runCatching {
            dbFactory.dbQuery {
                CalendarDateEntity.all().toList()
            }
        }.onFailure {
            logger.error("Error getting all calendar dates", it)
        }

    override suspend fun getCalendarDateById(id: Int): Result<CalendarDateEntity?> =
        runCatching {
            dbFactory.dbQuery {
                CalendarDateEntity.findById(id)
            }
        }.onFailure {
            logger.error("Error getting calendar date by id: $id", it)
        }

    override suspend fun addCalendarDate(calendar: CalendarDate): Result<CalendarDateEntity> =
        runCatching {
            dbFactory.dbQuery {
                CalendarDateEntity.new {
                    setProperties(calendar.serviceId, calendar.date, calendar.exceptionType)
                }
            }
        }.onFailure {
            logger.error("Error adding calendar date", it)
        }

    override suspend fun updateCalendarDate(calendarDate: CalendarDate): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                val existingCalendarDate = CalendarDateEntity.findById(calendarDate.id)
                if (existingCalendarDate != null) {
                    existingCalendarDate.apply {
                        setProperties(calendarDate.serviceId, calendarDate.date, calendarDate.exceptionType)
                    }
                    true
                } else {
                    throw IllegalArgumentException("Calendar date with ID ${calendarDate.id} does not exist")
                }
            }
        }.onFailure {
            logger.error("Error updating calendar date: $calendarDate", it)
        }

    override suspend fun deleteCalendarDate(id: Int): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                CalendarDateEntity.findById(id)?.delete() != null
            }
        }.onFailure {
            logger.error("Error deleting calendar date by id: $id", it)
        }

    private fun CalendarDateEntity.setProperties(
        serviceId: String,
        date: LocalDate?,
        exceptionType: ScheduleAdjustmentType?,
    ) {
        this.serviceId = EntityID(serviceId, CalendarsTable)
        this.date = date
        this.exceptionType = exceptionType
    }
}
