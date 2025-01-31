package com.trackmybus.theKeg.features.schedule.data.local.dao.calendar

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.entity.CalendarEntity
import com.trackmybus.theKeg.features.schedule.data.local.tables.CalendarsTable
import com.trackmybus.theKeg.features.schedule.data.remote.dto.CalendarDto
import com.trackmybus.theKeg.features.schedule.domain.model.Calendar
import io.ktor.util.logging.Logger
import org.jetbrains.exposed.dao.id.EntityID

class CalendarDaoImpl(private val logger: Logger, private val dbFactory: DatabaseFactory) : CalendarDao {
    override suspend fun getAllCalendars(): Result<List<CalendarEntity>> = runCatching {
        dbFactory.dbQuery {
            CalendarEntity.all().toList()
        }
    }.onFailure {
        logger.error("Error getting all calendars", it)
    }

    override suspend fun getCalendarById(id: String): Result<CalendarEntity?> = runCatching {
        dbFactory.dbQuery {
            CalendarEntity.findById(id)
        }
    }.onFailure {
        logger.error("Error getting calendar by id: $id", it)
    }

    override suspend fun addCalendar(calendar: Calendar): Result<CalendarEntity> = runCatching {
        dbFactory.dbQuery {
            CalendarEntity.new {
                setPropertiesFrom(calendar)
            }
        }
    }.onFailure {
        logger.error("Error adding calendar: $calendar", it)
    }

    override suspend fun updateCalendar(calendar: Calendar): Result<Boolean> = runCatching {
        dbFactory.dbQuery {
            val existingCalendar = CalendarEntity.findById(calendar.serviceId)
            if (existingCalendar != null) {
                existingCalendar.setPropertiesFrom(calendar)
                true
            } else {
                throw IllegalArgumentException("Calendar with ID ${calendar.serviceId} does not exist")
            }
        }
    }.onFailure {
        logger.error("Error updating calendar: $calendar", it)
    }

    override suspend fun deleteCalendar(id: String): Result<Boolean> = runCatching {
        dbFactory.dbQuery {
            CalendarEntity.findById(id)?.delete() != null
        }
    }.onFailure {
        logger.error("Error deleting calendar by id: $id", it)
    }

    private fun CalendarEntity.setPropertiesFrom(calendar: Calendar) {
        serviceId = EntityID(calendar.serviceId, CalendarsTable)
        monday = calendar.monday
        tuesday = calendar.tuesday
        wednesday = calendar.wednesday
        thursday = calendar.thursday
        friday = calendar.friday
        saturday = calendar.saturday
        sunday = calendar.sunday
        startDate = calendar.startDate
        endDate = calendar.endDate
    }
}