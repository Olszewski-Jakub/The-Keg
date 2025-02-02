package com.trackmybus.theKeg.features.schedule.data.local.dao.trip

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.entity.TripEntity
import com.trackmybus.theKeg.features.schedule.data.local.tables.CalendarsTable
import com.trackmybus.theKeg.features.schedule.data.local.tables.RoutesTable
import com.trackmybus.theKeg.features.schedule.data.local.tables.TripsTable
import com.trackmybus.theKeg.features.schedule.domain.model.Trip
import io.ktor.util.logging.Logger
import org.jetbrains.exposed.dao.id.EntityID

class TripDaoImpl(
    private val logger: Logger,
    private val dbFactory: DatabaseFactory,
) : TripDao {
    override suspend fun getAllTrips(): Result<List<TripEntity>> =
        runCatching {
            dbFactory.dbQuery {
                TripEntity.all().toList()
            }
        }.onFailure {
            logger.error("Error getting all trips", it)
        }

    override suspend fun getTripById(id: String): Result<TripEntity?> =
        runCatching {
            dbFactory.dbQuery {
                TripEntity.findById(id)
            }
        }.onFailure {
            logger.error("Error getting trip by id: $id", it)
        }

    override suspend fun addTrip(trip: Trip): Result<TripEntity> =
        runCatching {
            dbFactory.dbQuery {
                TripEntity.new {
                    setPropertiesFrom(trip)
                }
            }
        }.onFailure {
            logger.error("Error adding trip: $trip", it)
        }

    override suspend fun updateTrip(trip: Trip): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                val existingTrip = TripEntity.findById(trip.tripId)
                if (existingTrip != null) {
                    existingTrip.apply {
                        setPropertiesFrom(trip)
                    }
                    true
                } else {
                    throw IllegalArgumentException("Trip with ID ${trip.tripId} does not exist")
                }
            }
        }.onFailure {
            logger.error("Error updating trip: $trip", it)
        }

    override suspend fun deleteTrip(id: String): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                TripEntity.findById(id)?.delete() != null
            }
        }.onFailure {
            logger.error("Error deleting trip by id: $id", it)
        }

    private fun TripEntity.setPropertiesFrom(trip: Trip) {
        this.routeId = EntityID<String>(trip.routeId, RoutesTable)
        this.serviceId = EntityID<String>(trip.serviceId, CalendarsTable)
        this.tripId = EntityID<String>(trip.tripId, TripsTable)
        this.tripHeadsign = trip.tripHeadsign
        this.tripShortName = trip.tripShortName
        this.directionId = trip.directionId
        this.blockId = trip.blockId
        this.shapeId = trip.shapeId
    }
}
