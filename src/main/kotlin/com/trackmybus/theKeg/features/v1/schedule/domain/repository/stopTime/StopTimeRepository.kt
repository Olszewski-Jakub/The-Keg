package com.trackmybus.theKeg.features.v1.schedule.domain.repository.stopTime

import com.trackmybus.theKeg.features.v1.schedule.domain.model.StopTime

interface StopTimeRepository {
    suspend fun getAll(): Result<List<StopTime>>

    suspend fun getById(id: Int): Result<StopTime?>

    suspend fun add(calendar: StopTime): Result<StopTime>

    suspend fun update(calendar: StopTime): Result<Boolean>

    suspend fun deleteById(id: Int): Result<Boolean>
}
