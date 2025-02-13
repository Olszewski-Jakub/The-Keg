package com.trackmybus.theKeg.features.v1.schedule.domain.repository.agency

import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.agency.AgenciesDao
import com.trackmybus.theKeg.features.v1.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Agency
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class AgencyRepositoryImpl(
    private val logger: Logger,
    private val agenciesDao: AgenciesDao,
) : AgencyRepository {
    override suspend fun getAll(): Result<List<Agency>> {
        logger.info("Fetching all agencies")
        return agenciesDao
            .getAllAgencies()
            .mapResult { it.map { agencyEntity -> agencyEntity.toModel() } }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched all agencies") }
                result.onFailure { logger.error("Error fetching all agencies", it) }
            }
    }

    override suspend fun getById(id: String): Result<Agency?> {
        logger.info("Fetching agency with id: $id")
        return agenciesDao
            .getAgencyById(id)
            .mapResult { it?.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched agency with id: $id") }
                result.onFailure { logger.error("Error fetching agency with id: $id", it) }
            }
    }

    override suspend fun add(agency: Agency): Result<Agency> {
        logger.info("Adding agency: ${agency.agencyName}")
        return agenciesDao
            .addAgency(agency)
            .mapResult { it.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully added agency: ${agency.agencyName}") }
                result.onFailure { logger.error("Error adding agency: ${agency.agencyName}", it) }
            }
    }

    override suspend fun update(agency: Agency): Result<Boolean> {
        logger.info("Updating agency: ${agency.agencyName}")
        return agenciesDao
            .updateAgency(agency)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully updated agency: ${agency.agencyName}") }
                result.onFailure { logger.error("Error updating agency: ${agency.agencyName}", it) }
            }
    }

    override suspend fun deleteById(id: String): Result<Boolean> {
        logger.info("Deleting agency with id: $id")
        return agenciesDao
            .deleteAgency(id)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully deleted agency with id: $id") }
                result.onFailure { logger.error("Error deleting agency with id: $id", it) }
            }
    }
}
