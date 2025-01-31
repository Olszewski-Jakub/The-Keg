package com.trackmybus.theKeg.features.schedule.domain.repository.gtfs

import com.trackmybus.theKeg.features.schedule.data.remote.dto.GTFSDto
import com.trackmybus.theKeg.features.schedule.data.remote.service.GtfsScheduleService
import com.trackmybus.theKeg.features.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.schedule.domain.model.GTFS
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class GtfsScheduleRepositoryImpl(private val logger: Logger,private val gtfsScheduleService: GtfsScheduleService) : GtfsScheduleRepository {
    override suspend fun fetchGtfsData(): Result<GTFS> {
        return try {
            gtfsScheduleService.fetchGtfsData().mapResult { it.toModel() }
        } catch (e: Exception) {
            logger.error("Error fetching GTFS data", e)
            Result.failure(e)
        }
    }

    override fun test(): Result<GTFS> {
        return try {
            Result.success(GTFSDto().toModel())
        } catch (e: Exception) {
            logger.error("Error fetching GTFS data", e)
            Result.failure(e)
        }
    }
}