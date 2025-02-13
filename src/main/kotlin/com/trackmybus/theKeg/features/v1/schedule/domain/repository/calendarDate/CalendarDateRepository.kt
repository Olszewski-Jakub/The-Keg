package com.trackmybus.theKeg.features.v1.schedule.domain.repository.calendarDate

import com.trackmybus.theKeg.features.v1.schedule.domain.model.CalendarDate

interface CalendarDateRepository {
    suspend fun getAll(): Result<List<CalendarDate>>

    suspend fun getById(id: Int): Result<CalendarDate?>

    suspend fun add(calendar: CalendarDate): Result<CalendarDate>

    suspend fun update(calendar: CalendarDate): Result<Boolean>

    suspend fun deleteById(id: Int): Result<Boolean>
}
