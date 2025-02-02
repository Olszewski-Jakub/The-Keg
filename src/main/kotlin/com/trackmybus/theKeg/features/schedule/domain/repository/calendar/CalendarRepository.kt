package com.trackmybus.theKeg.features.schedule.domain.repository.calendar

import com.trackmybus.theKeg.features.schedule.domain.model.Calendar

interface CalendarRepository {
    suspend fun getAll(): Result<List<Calendar>>

    suspend fun getById(id: String): Result<Calendar?>

    suspend fun add(calendar: Calendar): Result<Calendar>

    suspend fun update(calendar: Calendar): Result<Boolean>

    suspend fun deleteById(id: String): Result<Boolean>
}
