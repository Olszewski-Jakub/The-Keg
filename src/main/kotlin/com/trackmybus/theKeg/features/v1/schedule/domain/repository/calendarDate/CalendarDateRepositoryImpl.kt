package com.trackmybus.theKeg.features.v1.schedule.domain.repository.calendarDate

import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.calendarDate.CalendarDatesDao
import com.trackmybus.theKeg.features.v1.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.schedule.domain.model.CalendarDate
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class CalendarDateRepositoryImpl(
    private val logger: Logger,
    private val calendarDatesDao: CalendarDatesDao,
) : CalendarDateRepository {
    override suspend fun getAll(): Result<List<CalendarDate>> {
        logger.info("Fetching all calendar dates")
        return calendarDatesDao
            .getAllCalendarDates()
            .mapResult { it.map { calendarDateEntity -> calendarDateEntity.toModel() } }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched all calendar dates") }
                result.onFailure { logger.error("Error fetching all calendar dates", it) }
            }
    }

    override suspend fun getById(id: Int): Result<CalendarDate?> {
        logger.info("Fetching calendar date with id: $id")
        return calendarDatesDao
            .getCalendarDateById(id)
            .mapResult { it?.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched calendar date with id: $id") }
                result.onFailure { logger.error("Error fetching calendar date with id: $id", it) }
            }
    }

    override suspend fun add(calendar: CalendarDate): Result<CalendarDate> {
        logger.info("Adding calendar date: ${calendar.date}")
        return calendarDatesDao
            .addCalendarDate(calendar)
            .mapResult { it.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully added calendar date: ${calendar.date}") }
                result.onFailure { logger.error("Error adding calendar date: ${calendar.date}", it) }
            }
    }

    override suspend fun update(calendar: CalendarDate): Result<Boolean> {
        logger.info("Updating calendar date: ${calendar.date}")
        return calendarDatesDao
            .updateCalendarDate(calendar)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully updated calendar date: ${calendar.date}") }
                result.onFailure { logger.error("Error updating calendar date: ${calendar.date}", it) }
            }
    }

    override suspend fun deleteById(id: Int): Result<Boolean> {
        logger.info("Deleting calendar date with id: $id")
        return calendarDatesDao
            .deleteCalendarDate(id)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully deleted calendar date with id: $id") }
                result.onFailure { logger.error("Error deleting calendar date with id: $id", it) }
            }
    }
}
