@file:Suppress("ktlint:standard:filename")

package com.trackmybus.di

import com.trackmybus.database.DatabaseFactoryForServerTest
import com.trackmybus.database.DatabaseFactoryForUnitTest
import com.trackmybus.theKeg.config.AppConfig
import com.trackmybus.theKeg.config.GtfsConfig
import com.trackmybus.theKeg.config.ServerConfig
import com.trackmybus.theKeg.database.DatabaseFactory
import org.koin.dsl.module

val testConfigModulesForUnitTest =
    module {
        single {
            AppConfig().apply {
                gtfsConfig =
                    GtfsConfig(
                        url = "https://www.transportforireland.ie/transitData/Data/GTFS_Realtime.zip",
                        gtfsFile = "GTFS_Realtime.zip",
                        agencyFile = "agency.txt",
                        calendarFile = "calendar.txt",
                        calendarDatesFile = "calendar_dates.txt",
                        routesFile = "routes.txt",
                        shapesFile = "shapes.txt",
                        stopsFile = "stops.txt",
                        stopTimesFile = "stop_times.txt",
                        tripsFile = "trips.txt",
                        feedInfoFile = "feed_info.txt",
                        outputDir = "temp/gtfs",
                    )
                serverConfig = ServerConfig(isProd = false)
            }
        }
        single<DatabaseFactory> { DatabaseFactoryForUnitTest(get()) }
        single { get<DatabaseFactory>().database }
    }

val testConfigModulesForIntegrationTest =
    module {
        single { AppConfig() }
        single<DatabaseFactory> { DatabaseFactoryForServerTest(get(), get()) }
        single { get<DatabaseFactory>().database }
    }
