@file:Suppress("ktlint:standard:filename")

package com.trackmybus.theKeg.features.v1.di

import com.trackmybus.theKeg.features.v1.data.local.ScheduleSchemaInitializer
import org.koin.dsl.module

val scheduleDatabaseModule =
    module {
        single { ScheduleSchemaInitializer() }
    }
