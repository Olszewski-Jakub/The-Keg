package com.trackmybus.theKeg.features.schedule.di

import com.trackmybus.theKeg.di.getLogger
import com.trackmybus.theKeg.features.schedule.data.local.manager.GtfsQueryManager
import com.trackmybus.theKeg.features.schedule.data.local.manager.GtfsQueryManagerImpl
import com.trackmybus.theKeg.features.schedule.data.remote.service.GtfsScheduleService
import com.trackmybus.theKeg.features.schedule.data.remote.service.GtfsScheduleServiceImpl
import org.koin.dsl.module

val serviceModules =
    module {
        single<GtfsScheduleService> { GtfsScheduleServiceImpl(getLogger<GtfsScheduleServiceImpl>(), get(), get()) }
        single<GtfsQueryManager> { GtfsQueryManagerImpl(getLogger<GtfsQueryManagerImpl>()) }
    }
