package com.trackmybus.di

import com.trackmybus.theKeg.di.csvModule
import com.trackmybus.theKeg.di.databaseModule
import com.trackmybus.theKeg.di.loggerModule
import com.trackmybus.theKeg.features.v1.di.scheduleModules
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.context.startKoin
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun configureKoinUnitTest() {
    startKoin {
        slf4jLogger()
        modules(
            testConfigModulesForUnitTest,
            csvModule,
            loggerModule,
            databaseModule,
            *scheduleModules.toTypedArray(),
        )
    }
}

fun Application.configureKoinServerTest() {
    install(Koin) {
        slf4jLogger()
        modules(
            testConfigModulesForIntegrationTest,
            csvModule,
            loggerModule,
            databaseModule,
            *scheduleModules.toTypedArray(),
        )
    }
}
