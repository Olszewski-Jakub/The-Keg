package com.trackmybus.theKeg.features.schedule.data.local.dao.shape

import com.trackmybus.theKeg.features.schedule.data.local.entity.ShapeEntity
import com.trackmybus.theKeg.features.schedule.domain.model.Shape

/**
 * Data Access Object (DAO) interface for managing shape data in the local database.
 */
interface ShapeDao {
    /**
     * Retrieves all shapes from the local database.
     * @return A [Result] containing a list of [ShapeEntity] objects.
     */
    suspend fun getAllShapes(): Result<List<ShapeEntity>>

    /**
     * Retrieves a shape by its ID.
     * @param id The ID of the shape to retrieve.
     * @return A [Result] containing the [ShapeEntity] object if found, or null if not found.
     */
    suspend fun getShapeById(id: Int): Result<ShapeEntity?>

    /**
     * Adds a new shape to the local database.
     * @param shape The [ShapeEntity] object to add.
     * @return A [Result] containing the newly added [ShapeEntity] object.
     */
    suspend fun addShape(shape: Shape): Result<ShapeEntity>

    /**
     * Updates an existing shape in the local database.
     * @param shape The [ShapeEntity] object containing updated information.
     * @return A [Result] indicating whether the update was successful.
     */
    suspend fun updateShape(shape: Shape): Result<Boolean>

    /**
     * Deletes a shape from the local database by its ID.
     * @param id The ID of the shape to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteShape(id: Int): Result<Boolean>
}
