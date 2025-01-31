package com.trackmybus.theKeg.features.schedule.di

import com.trackmybus.theKeg.features.schedule.data.local.ScheduleSchemaInitializer
import org.koin.dsl.module

val scheduleDatabaseModule = module {
    single { ScheduleSchemaInitializer() }
}