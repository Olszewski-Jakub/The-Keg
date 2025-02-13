package com.trackmybus.theKeg.features.v1.schedule.domain.repository.calendar

import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.calendar.CalendarDao
import com.trackmybus.theKeg.features.v1.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Calendar
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class CalendarRepositoryImpl(
    private val logger: Logger,
    private val calendarDao: CalendarDao,
) : CalendarRepository {
    override suspend fun getAll(): Result<List<Calendar>> {
        logger.info("Fetching all calendars")
        return calendarDao
            .getAllCalendars()
            .mapResult { it.map { calendarEntity -> calendarEntity.toModel() } }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched all calendars") }
                result.onFailure { logger.error("Error fetching all calendars", it) }
            }
    }

    override suspend fun getById(id: String): Result<Calendar?> {
        logger.info("Fetching calendar with id: $id")
        return calendarDao
            .getCalendarById(id)
            .mapResult { it?.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched calendar with id: $id") }
                result.onFailure { logger.error("Error fetching calendar with id: $id", it) }
            }
    }

    override suspend fun add(calendar: Calendar): Result<Calendar> {
        logger.info("Adding calendar: ${calendar.serviceId}")
        return calendarDao
            .addCalendar(calendar)
            .mapResult { it.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully added calendar: ${calendar.serviceId}") }
                result.onFailure { logger.error("Error adding calendar: ${calendar.serviceId}", it) }
            }
    }

    override suspend fun update(calendar: Calendar): Result<Boolean> {
        logger.info("Updating calendar: ${calendar.serviceId}")
        return calendarDao
            .updateCalendar(calendar)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully updated calendar: ${calendar.serviceId}") }
                result.onFailure { logger.error("Error updating calendar: ${calendar.serviceId}", it) }
            }
    }

    override suspend fun deleteById(id: String): Result<Boolean> {
        logger.info("Deleting calendar with id: $id")
        return calendarDao
            .deleteCalendar(id)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully deleted calendar with id: $id") }
                result.onFailure { logger.error("Error deleting calendar with id: $id", it) }
            }
    }
}
