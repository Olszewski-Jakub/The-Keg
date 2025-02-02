package com.trackmybus.theKeg.features.schedule.data.local.dao.calendarDate

import com.trackmybus.theKeg.features.schedule.data.local.entity.CalendarDateEntity
import com.trackmybus.theKeg.features.schedule.domain.model.CalendarDate

/**
 * Data Access Object (DAO) interface for managing calendar date data in the local database.
 */
interface CalendarDatesDao {
    /**
     * Retrieves all calendar dates from the local database.
     * @return A [Result] containing a list of [CalendarDateEntity] objects.
     */
    suspend fun getAllCalendarDates(): Result<List<CalendarDateEntity>>

    /**
     * Retrieves a calendar date by its ID.
     * @param id The ID of the calendar date to retrieve.
     * @return A [Result] containing the [CalendarDateEntity] object if found, or null if not found.
     */
    suspend fun getCalendarDateById(id: Int): Result<CalendarDateEntity?>

    /**
     * Adds a new calendar date to the local database.
     * @param serviceId The ID of the service.
     * @param date The date of the calendar entry.
     * @param exceptionType The type of schedule adjustment.
     * @return A [Result] containing the newly added [CalendarDateEntity] object.
     */
    suspend fun addCalendarDate(calendar: CalendarDate): Result<CalendarDateEntity>

    /**
     * Updates an existing calendar date in the local database.
     * @param calendarDate The [CalendarDateEntity] object containing updated information.
     * @return A [Result] indicating whether the update was successful.
     */
    suspend fun updateCalendarDate(calendarDate: CalendarDate): Result<Boolean>

    /**
     * Deletes a calendar date from the local database by its ID.
     * @param id The ID of the calendar date to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteCalendarDate(id: Int): Result<Boolean>
}
