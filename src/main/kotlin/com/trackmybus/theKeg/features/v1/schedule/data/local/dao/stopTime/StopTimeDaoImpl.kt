package com.trackmybus.theKeg.features.v1.schedule.data.local.dao.stopTime

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.schedule.data.local.entity.StopTimeEntity
import com.trackmybus.theKeg.features.v1.schedule.data.local.tables.StopsTable
import com.trackmybus.theKeg.features.v1.schedule.data.local.tables.TripsTable
import com.trackmybus.theKeg.features.v1.schedule.domain.model.StopTime
import io.ktor.util.logging.Logger
import org.jetbrains.exposed.dao.id.EntityID

class StopTimeDaoImpl(
    private val logger: Logger,
    private val dbFactory: DatabaseFactory,
) : StopTimeDao {
    override suspend fun getAllStopTimes(): Result<List<StopTimeEntity>> =
        runCatching {
            dbFactory.dbQuery {
                StopTimeEntity.all().toList()
            }
        }.onFailure {
            logger.error("Error getting all stop times", it)
        }

    override suspend fun getStopTimeById(id: Int): Result<StopTimeEntity?> =
        runCatching {
            dbFactory.dbQuery {
                StopTimeEntity.findById(id)
            }
        }.onFailure {
            logger.error("Error getting stop time by id: $id", it)
        }

    override suspend fun addStopTime(stopTime: StopTime): Result<StopTimeEntity> =
        runCatching {
            dbFactory.dbQuery {
                StopTimeEntity.new {
                    setPropertiesFrom(stopTime)
                }
            }
        }.onFailure {
            logger.error("Error adding stop time: $stopTime", it)
        }

    override suspend fun updateStopTime(stopTime: StopTime): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                val existingStopTime = StopTimeEntity.findById(stopTime.id)
                if (existingStopTime != null) {
                    existingStopTime.apply {
                        setPropertiesFrom(stopTime)
                    }
                    true
                } else {
                    throw IllegalArgumentException("Stop time with ID ${stopTime.id} does not exist")
                }
            }
        }.onFailure {
            logger.error("Error updating stop time: $stopTime", it)
        }

    override suspend fun deleteStopTime(id: Int): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                StopTimeEntity.findById(id)?.delete() != null
            }
        }.onFailure {
            logger.error("Error deleting stop time by id: $id", it)
        }

    private fun StopTimeEntity.setPropertiesFrom(stopTime: StopTime) {
        this.tripId = EntityID<String>(stopTime.tripId, TripsTable)
        this.arrivalTime = stopTime.arrivalTime
        this.departureTime = stopTime.departureTime
        this.stopId = EntityID<String>(stopTime.stopId, StopsTable)
        this.stopSequence = stopTime.stopSequence
        this.stopHeadsign = stopTime.stopHeadsign
        this.pickupType = stopTime.pickupType
        this.dropOffType = stopTime.dropOffType
        this.timepoint = stopTime.timepoint
    }
}
