package com.trackmybus.theKeg.features.v1.schedule.data.local.dao.trip

import com.trackmybus.theKeg.features.v1.schedule.data.local.entity.TripEntity
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Trip

/**
 * Data Access Object (DAO) interface for managing trip data in the local database.
 */
interface TripDao {
    /**
     * Retrieves all trips from the local database.
     * @return A [Result] containing a list of [TripEntity] objects.
     */
    suspend fun getAllTrips(): Result<List<TripEntity>>

    /**
     * Retrieves a trip by its ID.
     * @param id The ID of the trip to retrieve.
     * @return A [Result] containing the [TripEntity] object if found, or null if not found.
     */
    suspend fun getTripById(id: String): Result<TripEntity?>

    /**
     * Adds a new trip to the local database.
     * @param trip The [TripEntity] object to add.
     * @return A [Result] containing the newly added [TripEntity] object.
     */
    suspend fun addTrip(trip: Trip): Result<TripEntity>

    /**
     * Updates an existing trip in the local database.
     * @param trip The [TripEntity] object containing updated information.
     * @return A [Result] indicating whether the update was successful.
     */
    suspend fun updateTrip(trip: Trip): Result<Boolean>

    /**
     * Deletes a trip from the local database by its ID.
     * @param id The ID of the trip to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteTrip(id: String): Result<Boolean>
}
