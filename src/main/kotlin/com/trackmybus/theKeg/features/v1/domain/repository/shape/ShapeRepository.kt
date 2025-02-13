package com.trackmybus.theKeg.features.v1.domain.repository.shape

import com.trackmybus.theKeg.features.v1.domain.model.Shape

interface ShapeRepository {
    suspend fun getAll(): Result<List<Shape>>

    suspend fun getById(id: Int): Result<Shape?>

    suspend fun add(calendar: Shape): Result<Shape>

    suspend fun update(calendar: Shape): Result<Boolean>

    suspend fun deleteById(id: Int): Result<Boolean>

    suspend fun getByShapeId(shapeId: String): Result<List<Shape>>
}
