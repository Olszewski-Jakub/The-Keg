package com.trackmybus.theKeg.features.schedule.data.local.dao.shape

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.entity.ShapeEntity
import com.trackmybus.theKeg.features.schedule.domain.model.Shape
import io.ktor.util.logging.Logger

class ShapeDaoImpl(
    private val logger: Logger,
    private val dbFactory: DatabaseFactory,
) : ShapeDao {
    override suspend fun getAllShapes(): Result<List<ShapeEntity>> =
        runCatching {
            dbFactory.dbQuery {
                ShapeEntity.all().toList()
            }
        }.onFailure {
            logger.error("Error getting all shapes", it)
        }

    override suspend fun getShapeById(id: Int): Result<ShapeEntity?> =
        runCatching {
            dbFactory.dbQuery {
                ShapeEntity.findById(id)
            }
        }.onFailure {
            logger.error("Error getting shape by id: $id", it)
        }

    override suspend fun addShape(shape: Shape): Result<ShapeEntity> =
        runCatching {
            dbFactory.dbQuery {
                ShapeEntity.new {
                    setPropertiesFrom(shape)
                }
            }
        }.onFailure {
            logger.error("Error adding shape: $shape", it)
        }

    override suspend fun updateShape(shape: Shape): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                val existingShape = ShapeEntity.findById(shape.id)
                if (existingShape != null) {
                    existingShape.apply {
                        setPropertiesFrom(shape)
                    }
                    true
                } else {
                    throw IllegalArgumentException("Shape with ID ${shape.id} does not exist")
                }
            }
        }.onFailure {
            logger.error("Error updating shape: $shape", it)
        }

    override suspend fun deleteShape(id: Int): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                ShapeEntity.findById(id)?.delete() != null
            }
        }.onFailure {
            logger.error("Error deleting shape by id: $id", it)
        }

    private fun ShapeEntity.setPropertiesFrom(shape: Shape) {
        this.shapeId = shape.shapeId
        this.shapePtLat = shape.shapePtLat
        this.shapePtLon = shape.shapePtLon
        this.shapePtSequence = shape.shapePtSequence
        this.shapeDistTraveled = shape.shapeDistTraveled
    }
}
