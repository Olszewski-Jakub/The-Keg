package com.trackmybus.theKeg.features.schedule.domain.repository.trip

import com.trackmybus.theKeg.features.schedule.domain.model.Trip

interface TripRepository {
    suspend fun getAll(): Result<List<Trip>>

    suspend fun getById(id: String): Result<Trip?>

    suspend fun add(calendar: Trip): Result<Trip>

    suspend fun update(calendar: Trip): Result<Boolean>

    suspend fun deleteById(id: String): Result<Boolean>
}
