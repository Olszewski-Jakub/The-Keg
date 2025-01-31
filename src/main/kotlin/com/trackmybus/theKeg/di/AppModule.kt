package com.trackmybus.theKeg.di

import com.trackmybus.theKeg.features.schedule.di.scheduleModules
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            configModule,
            csvModule,
            loggerModule,
            databaseModule,
            *scheduleModules.toTypedArray(),
        )
    }
}