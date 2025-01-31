package com.trackmybus.theKeg.features.schedule.domain.repository.route

import com.trackmybus.theKeg.features.schedule.domain.model.Route

interface RouteRepository {
    suspend fun getAll(): Result<List<Route>>
    suspend fun getById(id: String): Result<Route?>
    suspend fun add(calendar: Route): Result<Route>
    suspend fun update(calendar: Route): Result<Boolean>
    suspend fun deleteById(id: String): Result<Boolean>
}