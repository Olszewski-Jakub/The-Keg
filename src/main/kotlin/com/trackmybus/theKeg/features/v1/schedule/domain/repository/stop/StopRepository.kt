package com.trackmybus.theKeg.features.v1.schedule.domain.repository.stop

import com.trackmybus.theKeg.features.v1.schedule.domain.model.Stop

interface StopRepository {
    suspend fun getAll(): Result<List<Stop>>

    suspend fun getById(id: String): Result<Stop?>

    suspend fun add(calendar: Stop): Result<Stop>

    suspend fun update(calendar: Stop): Result<Boolean>

    suspend fun deleteById(id: String): Result<Boolean>
}
