package com.trackmybus.theKeg.features.schedule.data.local.dao.stopTime

import com.trackmybus.theKeg.features.schedule.data.local.entity.StopTimeEntity
import com.trackmybus.theKeg.features.schedule.domain.model.StopTime

/**
 * Data Access Object (DAO) interface for managing stop time data in the local database.
 */
interface StopTimeDao {
    /**
     * Retrieves all stop times from the local database.
     * @return A [Result] containing a list of [StopTimeEntity] objects.
     */
    suspend fun getAllStopTimes(): Result<List<StopTimeEntity>>

    /**
     * Retrieves a stop time by its ID.
     * @param id The ID of the stop time to retrieve.
     * @return A [Result] containing the [StopTimeEntity] object if found, or null if not found.
     */
    suspend fun getStopTimeById(id: Int): Result<StopTimeEntity?>

    /**
     * Adds a new stop time to the local database.
     * @param stopTime The [StopTimeEntity] object to add.
     * @return A [Result] containing the newly added [StopTimeEntity] object.
     */
    suspend fun addStopTime(stopTime: StopTime): Result<StopTimeEntity>

    /**
     * Updates an existing stop time in the local database.
     * @param stopTime The [StopTimeEntity] object containing updated information.
     * @return A [Result] indicating whether the update was successful.
     */
    suspend fun updateStopTime(stopTime: StopTime): Result<Boolean>

    /**
     * Deletes a stop time from the local database by its ID.
     * @param id The ID of the stop time to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteStopTime(id: Int): Result<Boolean>
}
