package com.trackmybus.theKeg.features.v1.schedule.domain.repository.stopTime

import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.stopTime.StopTimeDao
import com.trackmybus.theKeg.features.v1.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.schedule.domain.model.StopTime
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class StopTimeRepositoryImpl(
    private val logger: Logger,
    private val stopTimeDao: StopTimeDao,
) : StopTimeRepository {
    override suspend fun getAll(): Result<List<StopTime>> {
        logger.info("Fetching all stop times")
        return stopTimeDao
            .getAllStopTimes()
            .mapResult { it.map { stopTimeEntity -> stopTimeEntity.toModel() } }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched all stop times") }
                result.onFailure { logger.error("Error fetching all stop times", it) }
            }
    }

    override suspend fun getById(id: Int): Result<StopTime?> {
        logger.info("Fetching stop time with id: $id")
        return stopTimeDao
            .getStopTimeById(id)
            .mapResult { it?.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched stop time with id: $id") }
                result.onFailure { logger.error("Error fetching stop time with id: $id", it) }
            }
    }

    override suspend fun add(calendar: StopTime): Result<StopTime> {
        logger.info("Adding stop time: ${calendar.stopId}")
        return stopTimeDao
            .addStopTime(calendar)
            .mapResult { it.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully added stop time: ${calendar.stopId}") }
                result.onFailure { logger.error("Error adding stop time: ${calendar.stopId}", it) }
            }
    }

    override suspend fun update(calendar: StopTime): Result<Boolean> {
        logger.info("Updating stop time: ${calendar.stopId}")
        return stopTimeDao
            .updateStopTime(calendar)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully updated stop time: ${calendar.stopId}") }
                result.onFailure { logger.error("Error updating stop time: ${calendar.stopId}", it) }
            }
    }

    override suspend fun deleteById(id: Int): Result<Boolean> {
        logger.info("Deleting stop time with id: $id")
        return stopTimeDao
            .deleteStopTime(id)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully deleted stop time with id: $id") }
                result.onFailure { logger.error("Error deleting stop time with id: $id", it) }
            }
    }
}
