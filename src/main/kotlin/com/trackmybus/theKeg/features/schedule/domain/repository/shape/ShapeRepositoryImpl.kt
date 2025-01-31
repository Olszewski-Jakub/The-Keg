package com.trackmybus.theKeg.features.schedule.domain.repository.shape

import com.trackmybus.theKeg.features.schedule.data.local.dao.shape.ShapeDao
import com.trackmybus.theKeg.features.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.schedule.domain.model.Shape
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class ShapeRepositoryImpl(private val logger: Logger, private val shapeDao: ShapeDao): ShapeRepository{
    override suspend fun getAll(): Result<List<Shape>> {
        logger.info("Fetching all shapes")
        return shapeDao.getAllShapes()
            .mapResult { it.map { shapeEntity -> shapeEntity.toModel() } }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched all shapes") }
                result.onFailure { logger.error("Error fetching all shapes", it) }
            }
    }

    override suspend fun getById(id: Int): Result<Shape?> {
        logger.info("Fetching shape with id: $id")
        return shapeDao.getShapeById(id)
            .mapResult { it?.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched shape with id: $id") }
                result.onFailure { logger.error("Error fetching shape with id: $id", it) }
            }
    }

    override suspend fun add(calendar: Shape): Result<Shape> {
        logger.info("Adding shape: ${calendar.shapeId}")
        return shapeDao.addShape(calendar)
            .mapResult { it.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully added shape: ${calendar.shapeId}") }
                result.onFailure { logger.error("Error adding shape: ${calendar.shapeId}", it) }
            }
    }

    override suspend fun update(calendar: Shape): Result<Boolean> {
        logger.info("Updating shape: ${calendar.shapeId}")
        return shapeDao.updateShape(calendar)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully updated shape: ${calendar.shapeId}") }
                result.onFailure { logger.error("Error updating shape: ${calendar.shapeId}", it) }
            }
    }

    override suspend fun deleteById(id: Int): Result<Boolean> {
        logger.info("Deleting shape with id: $id")
        return shapeDao.deleteShape(id)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully deleted shape with id: $id") }
                result.onFailure { logger.error("Error deleting shape with id: $id", it) }
            }
    }
}