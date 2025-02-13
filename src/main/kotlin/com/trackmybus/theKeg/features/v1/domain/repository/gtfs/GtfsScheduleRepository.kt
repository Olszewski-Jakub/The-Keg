package com.trackmybus.theKeg.features.v1.domain.repository.gtfs

import com.trackmybus.theKeg.features.v1.domain.model.GTFS

interface GtfsScheduleRepository {
    suspend fun fetchGtfsData(): Result<GTFS>

    fun test(): Result<GTFS>
}
