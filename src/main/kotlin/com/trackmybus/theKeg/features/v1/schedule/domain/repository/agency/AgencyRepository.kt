package com.trackmybus.theKeg.features.v1.schedule.domain.repository.agency

import com.trackmybus.theKeg.features.v1.schedule.domain.model.Agency

/**
 * Repository interface for managing agencies.
 */
interface AgencyRepository {
    /**
     * Fetches all agencies.
     *
     * @return A [Result] containing a list of [Agency] objects if successful, or an error if not.
     */
    suspend fun getAll(): Result<List<Agency>>

    /**
     * Fetches an agency by its ID.
     *
     * @param id The ID of the agency to fetch.
     * @return A [Result] containing the [Agency] object if found, or null if not found, or an error if not.
     */
    suspend fun getById(id: String): Result<Agency?>

    /**
     * Adds a new agency.
     *
     * @param agency The [Agency] object to add.
     * @return A [Result] containing the added [Agency] object if successful, or an error if not.
     */
    suspend fun add(agency: Agency): Result<Agency>

    /**
     * Updates an existing agency.
     *
     * @param agency The [Agency] object with updated information.
     * @return A [Result] indicating success (true) or failure (false), or an error if not.
     */
    suspend fun update(agency: Agency): Result<Boolean>

    /**
     * Deletes an agency by its ID.
     *
     * @param id The ID of the agency to delete.
     * @return A [Result] indicating success (true) or failure (false), or an error if not.
     */
    suspend fun deleteById(id: String): Result<Boolean>
}
