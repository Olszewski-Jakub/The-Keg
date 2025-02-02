package com.trackmybus.theKeg.features.schedule.domain.repository.stop

import com.trackmybus.theKeg.features.schedule.data.local.dao.stop.StopDao
import com.trackmybus.theKeg.features.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.schedule.domain.model.Stop
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class StopRepositoryImpl(
    private val logger: Logger,
    private val stopDao: StopDao,
) : StopRepository {
    override suspend fun getAll(): Result<List<Stop>> {
        logger.info("Fetching all stops")
        return stopDao
            .getAllStops()
            .mapResult { it.map { stopEntity -> stopEntity.toModel() } }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched all stops") }
                result.onFailure { logger.error("Error fetching all stops", it) }
            }
    }

    override suspend fun getById(id: String): Result<Stop?> {
        logger.info("Fetching stop with id: $id")
        return stopDao
            .getStopById(id)
            .mapResult { it?.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched stop with id: $id") }
                result.onFailure { logger.error("Error fetching stop with id: $id", it) }
            }
    }

    override suspend fun add(calendar: Stop): Result<Stop> {
        logger.info("Adding stop: ${calendar.stopId}")
        return stopDao
            .addStop(calendar)
            .mapResult { it.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully added stop: ${calendar.stopId}") }
                result.onFailure { logger.error("Error adding stop: ${calendar.stopId}", it) }
            }
    }

    override suspend fun update(calendar: Stop): Result<Boolean> {
        logger.info("Updating stop: ${calendar.stopId}")
        return stopDao
            .updateStop(calendar)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully updated stop: ${calendar.stopId}") }
                result.onFailure { logger.error("Error updating stop: ${calendar.stopId}", it) }
            }
    }

    override suspend fun deleteById(id: String): Result<Boolean> {
        logger.info("Deleting stop with id: $id")
        return stopDao
            .deleteStop(id)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully deleted stop with id: $id") }
                result.onFailure { logger.error("Error deleting stop with id: $id", it) }
            }
    }
}
