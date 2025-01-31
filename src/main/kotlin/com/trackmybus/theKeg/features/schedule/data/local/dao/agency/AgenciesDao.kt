package com.trackmybus.theKeg.features.schedule.data.local.dao.agency

import com.trackmybus.theKeg.features.schedule.data.local.entity.AgencyEntity
import com.trackmybus.theKeg.features.schedule.domain.model.Agency

/**
 * Data Access Object (DAO) interface for managing agency data in the local database.
 */
interface AgenciesDao {

    /**
     * Retrieves all agencies from the local database.
     * @return A [Result] containing a list of [AgencyEntity] objects.
     */
    suspend fun getAllAgencies(): Result<List<AgencyEntity>>

    /**
     * Retrieves an agency by its ID.
     * @param id The ID of the agency to retrieve.
     * @return A [Result] containing the [AgencyEntity] object if found, or null if not found.
     */
    suspend fun getAgencyById(id: String): Result<AgencyEntity?>

    /**
     * Adds a new agency to the local database.
     * @param agencyName The name of the agency.
     * @param agencyUrl The URL of the agency.
     * @param agencyTimezone The timezone of the agency.
     * @return A [Result] containing the newly added [AgencyEntity] object.
     */
    suspend fun addAgency(agency: Agency): Result<AgencyEntity>

    /**
     * Updates an existing agency in the local database.
     * @param agency The [AgencyEntity] object containing updated information.
     * @return A [Result] indicating whether the update was successful.
     */
    suspend fun updateAgency(agency: Agency): Result<Boolean>

    /**
     * Deletes an agency from the local database by its ID.
     * @param id The ID of the agency to delete.
     * @return A [Result] indicating whether the deletion was successful.
     */
    suspend fun deleteAgency(id: String): Result<Boolean>
}