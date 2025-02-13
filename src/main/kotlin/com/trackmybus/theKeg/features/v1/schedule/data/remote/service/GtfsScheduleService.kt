package com.trackmybus.theKeg.features.v1.schedule.data.remote.service

import com.trackmybus.theKeg.features.v1.schedule.data.remote.dto.GTFSDto

interface GtfsScheduleService {
    suspend fun fetchGtfsData(): Result<GTFSDto>
}
