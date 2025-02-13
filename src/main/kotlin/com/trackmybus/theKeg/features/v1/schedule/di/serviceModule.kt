@file:Suppress("ktlint:standard:filename")

package com.trackmybus.theKeg.features.v1.schedule.di

import com.trackmybus.theKeg.di.getLogger
import com.trackmybus.theKeg.features.v1.schedule.data.local.manager.GtfsQueryManager
import com.trackmybus.theKeg.features.v1.schedule.data.local.manager.GtfsQueryManagerImpl
import com.trackmybus.theKeg.features.v1.schedule.data.remote.service.GtfsScheduleService
import com.trackmybus.theKeg.features.v1.schedule.data.remote.service.GtfsScheduleServiceImpl
import org.koin.dsl.module

val serviceModules =
    module {
        single<GtfsScheduleService> { GtfsScheduleServiceImpl(getLogger<GtfsScheduleServiceImpl>(), get(), get()) }
        single<GtfsQueryManager> { GtfsQueryManagerImpl(getLogger<GtfsQueryManagerImpl>()) }
    }
