package com.trackmybus.theKeg.features.schedule.domain.repository.shape

import com.trackmybus.theKeg.features.schedule.domain.model.Shape

interface ShapeRepository {
    suspend fun getAll(): Result<List<Shape>>

    suspend fun getById(id: Int): Result<Shape?>

    suspend fun add(calendar: Shape): Result<Shape>

    suspend fun update(calendar: Shape): Result<Boolean>

    suspend fun deleteById(id: Int): Result<Boolean>
}
