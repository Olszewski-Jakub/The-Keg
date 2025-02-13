package com.trackmybus.theKeg.features.v1.domain.repository.gtfs

import com.trackmybus.theKeg.features.v1.data.remote.dto.GTFSDto
import com.trackmybus.theKeg.features.v1.data.remote.service.GtfsScheduleService
import com.trackmybus.theKeg.features.v1.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.domain.model.GTFS
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger

class GtfsScheduleRepositoryImpl(
    private val logger: Logger,
    private val gtfsScheduleService: GtfsScheduleService,
) : GtfsScheduleRepository {
    override suspend fun fetchGtfsData(): Result<GTFS> =
        try {
            gtfsScheduleService.fetchGtfsData().mapResult { it.toModel() }
        } catch (e: Exception) {
            logger.error("Error fetching GTFS data", e)
            Result.failure(e)
        }

    override fun test(): Result<GTFS> =
        try {
            Result.success(GTFSDto().toModel())
        } catch (e: Exception) {
            logger.error("Error fetching GTFS data", e)
            Result.failure(e)
        }
}
