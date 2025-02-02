package com.trackmybus.theKeg.features.schedule.data.local.dao.stop

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.entity.StopEntity
import com.trackmybus.theKeg.features.schedule.data.local.tables.StopsTable
import com.trackmybus.theKeg.features.schedule.domain.model.Stop
import io.ktor.util.logging.Logger
import org.jetbrains.exposed.dao.id.EntityID

class StopDaoImpl(
    private val logger: Logger,
    private val dbFactory: DatabaseFactory,
) : StopDao {
    override suspend fun getAllStops(): Result<List<StopEntity>> =
        runCatching {
            dbFactory.dbQuery {
                StopEntity.all().toList()
            }
        }.onFailure {
            logger.error("Error getting all stops", it)
        }

    override suspend fun getStopById(id: String): Result<StopEntity?> =
        runCatching {
            dbFactory.dbQuery {
                StopEntity.findById(id)
            }
        }.onFailure {
            logger.error("Error getting stop by id: $id", it)
        }

    override suspend fun addStop(stop: Stop): Result<StopEntity> =
        runCatching {
            dbFactory.dbQuery {
                StopEntity.new {
                    setPropertiesFrom(stop)
                }
            }
        }.onFailure {
            logger.error("Error adding stop: $stop", it)
        }

    override suspend fun updateStop(stop: Stop): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                val existingStop = StopEntity.findById(stop.stopId)
                if (existingStop != null) {
                    existingStop.apply {
                        setPropertiesFrom(stop)
                    }
                    true
                } else {
                    throw IllegalArgumentException("Stop with ID ${stop.stopId} does not exist")
                }
            }
        }.onFailure {
            logger.error("Error updating stop: $stop", it)
        }

    override suspend fun deleteStop(id: String): Result<Boolean> =
        runCatching {
            dbFactory.dbQuery {
                StopEntity.findById(id)?.delete() != null
            }
        }.onFailure {
            logger.error("Error deleting stop by id: $id", it)
        }

    private fun StopEntity.setPropertiesFrom(stop: Stop) {
        this.stopId = EntityID<String>(stop.stopId, StopsTable)
        this.stopCode = stop.stopCode
        this.stopName = stop.stopName
        this.stopLat = stop.stopLat
        this.stopLon = stop.stopLon
        this.stopDesc = stop.stopDesc
        this.zoneId = stop.zoneId
        this.stopUrl = stop.stopUrl
        this.locationType = stop.locationType
        this.parentStation = stop.parentStation
    }
}
