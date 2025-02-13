package com.trackmybus.theKeg.features.v1.schedule.data.local.dao.stop

import com.trackmybus.theKeg.features.v1.schedule.data.local.entity.StopEntity
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Stop

/**
 * Data Access Object (DAO) interface for managing stop data in the local database.
 */
interface StopDao {
    /**
     * Retrieves all stops from the local database.
     * @return A [Result] containing a list of [StopEntity] objects.
     */
    suspend fun getAllStops(): Result<List<StopEntity>>

    /**
     * Retrieves a stop by its ID.
     * @param id The ID of the stop to retrieve.
     * @return A [Result] containing the [StopEntity] object if found, or null if not found.
     */
    suspend fun getStopById(id: String): Result<StopEntity?>

    /**
     * Adds a new stop to the local database.
     * @param stop The [StopEntity] object to add.
     * @return A [Result] containing the newly added [StopEntity] object.
     */
    suspend fun addStop(stop: Stop): Result<StopEntity>

    /**
     * Updates an existing stop in the local database.
     * @param stop The [StopEntity] object containing updated information.
     * @return A [Result] indicating whether the update was successful.
     */
    suspend fun updateStop(stop: Stop): Result<Boolean>

    /**
     * Deletes a stop from the local database by its ID.
     * @param id The ID of the stop to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteStop(id: String): Result<Boolean>
}
