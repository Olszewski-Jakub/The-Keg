package com.trackmybus.theKeg.features.schedule.domain.repository.gtfs

import com.trackmybus.theKeg.features.schedule.domain.model.GTFS

interface GtfsScheduleRepository {
    suspend fun fetchGtfsData(): Result<GTFS>

    fun test(): Result<GTFS>
}
