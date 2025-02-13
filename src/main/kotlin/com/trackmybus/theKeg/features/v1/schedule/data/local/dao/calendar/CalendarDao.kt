package com.trackmybus.theKeg.features.v1.schedule.data.local.dao.calendar

import com.trackmybus.theKeg.features.v1.schedule.data.local.entity.CalendarEntity
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Calendar

/**
 * Data Access Object (DAO) interface for managing calendar data in the local database.
 */
interface CalendarDao {
    /**
     * Retrieves all calendars from the local database.
     * @return A [Result] containing a list of [CalendarEntity] objects.
     */
    suspend fun getAllCalendars(): Result<List<CalendarEntity>>

    /**
     * Retrieves a calendar by its ID.
     * @param id The ID of the calendar to retrieve.
     * @return A [Result] containing the [CalendarEntity] object if found, or null if not found.
     */
    suspend fun getCalendarById(id: String): Result<CalendarEntity?>

    /**
     * Adds a new calendar to the local database.
     * @param calendar The [CalendarEntity] object to add.
     * @return A [Result] containing the newly added [CalendarEntity] object.
     */
    suspend fun addCalendar(calendar: Calendar): Result<CalendarEntity>

    /**
     * Updates an existing calendar in the local database.
     * @param calendar The [CalendarEntity] object containing updated information.
     * @return A [Result] indicating whether the update was successful.
     */
    suspend fun updateCalendar(calendar: Calendar): Result<Boolean>

    /**
     * Deletes a calendar from the local database by its ID.
     * @param id The ID of the calendar to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteCalendar(id: String): Result<Boolean>
}
