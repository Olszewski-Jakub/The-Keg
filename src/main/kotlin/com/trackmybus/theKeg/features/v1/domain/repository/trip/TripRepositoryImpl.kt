package com.trackmybus.theKeg.features.v1.domain.repository.trip

import com.trackmybus.theKeg.features.v1.data.local.dao.trip.TripDao
import com.trackmybus.theKeg.features.v1.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.domain.model.Trip
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class TripRepositoryImpl(
    private val logger: Logger,
    private val tripDao: TripDao,
) : TripRepository {
    override suspend fun getAll(): Result<List<Trip>> {
        logger.info("Fetching all trips")
        return tripDao
            .getAllTrips()
            .mapResult { it.map { tripEntity -> tripEntity.toModel() } }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched all trips") }
                result.onFailure { logger.error("Error fetching all trips", it) }
            }
    }

    override suspend fun getById(id: String): Result<Trip?> {
        logger.info("Fetching trip with id: $id")
        return tripDao
            .getTripById(id)
            .mapResult { it?.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully fetched trip with id: $id") }
                result.onFailure { logger.error("Error fetching trip with id: $id", it) }
            }
    }

    override suspend fun add(calendar: Trip): Result<Trip> {
        logger.info("Adding trip: ${calendar.tripId}")
        return tripDao
            .addTrip(calendar)
            .mapResult { it.toModel() }
            .also { result ->
                result.onSuccess { logger.info("Successfully added trip: ${calendar.tripId}") }
                result.onFailure { logger.error("Error adding trip: ${calendar.tripId}", it) }
            }
    }

    override suspend fun update(calendar: Trip): Result<Boolean> {
        logger.info("Updating trip: ${calendar.tripId}")
        return tripDao
            .updateTrip(calendar)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully updated trip: ${calendar.tripId}") }
                result.onFailure { logger.error("Error updating trip: ${calendar.tripId}", it) }
            }
    }

    override suspend fun deleteById(id: String): Result<Boolean> {
        logger.info("Deleting trip with id: $id")
        return tripDao
            .deleteTrip(id)
            .mapResult { it }
            .also { result ->
                result.onSuccess { logger.info("Successfully deleted trip with id: $id") }
                result.onFailure { logger.error("Error deleting trip with id: $id", it) }
            }
    }
}
