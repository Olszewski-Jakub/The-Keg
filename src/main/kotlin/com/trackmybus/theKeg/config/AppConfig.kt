package com.trackmybus.theKeg.config

import io.ktor.server.application.*
import org.koin.ktor.ext.inject

data class ServerConfig(
    val isProd: Boolean
)

data class PostgresConfig(
    val driverClass: String,
    val jdbcUrl: String,
    val database: String,
    val user: String,
    val password: String,
    val maxPoolSize: Int,
    val autoCommit: Boolean
)

data class GtfsConfig(
    val url: String,
    val gtfsFile: String,
    val outputDir: String,
    val agencyFile: String,
    val calendarFile: String,
    val calendarDatesFile: String,
    val routesFile: String,
    val shapesFile: String,
    val stopsFile: String,
    val stopTimesFile: String,
    val tripsFile: String,
    val feedInfoFile: String
)

class AppConfig {
    lateinit var serverConfig: ServerConfig
    lateinit var postgresConfig: PostgresConfig
    lateinit var gtfsConfig: GtfsConfig
}

fun Application.setupConfig() {
    val appConfig by inject<AppConfig>()

    // Server
    val serverObject = environment.config.config("ktor.server")
    val isProd = serverObject.property("isProd").getString().toBoolean()
    appConfig.serverConfig = ServerConfig(isProd)

    // Postgres
    val driverClass = environment.config.property("postgres.driverClass").getString()
    val database = environment.config.property("postgres.database").getString()
    val jdbcUrl = environment.config.property("postgres.jdbcURL").getString()
    val user = environment.config.property("postgres.user").getString()
    val password = environment.config.property("postgres.password").getString()
    val maxPoolSize = environment.config.property("postgres.maxPoolSize").getString().toInt()
    val autoCommit = environment.config.property("postgres.autoCommit").getString().toBoolean()

    appConfig.postgresConfig = PostgresConfig(
        driverClass = driverClass,
        jdbcUrl = jdbcUrl,
        database = database,
        user = user,
        password = password,
        maxPoolSize = maxPoolSize,
        autoCommit = autoCommit
    )

    // GTFS
    val gtfsUrl = environment.config.property("gtfs.url").getString()
    val gtfsFile = environment.config.property("gtfs.gtfsFile").getString()
    val agencyFile = environment.config.property("gtfs.agencyFile").getString()
    val calendarFile = environment.config.property("gtfs.calendarFile").getString()
    val calendarDatesFile = environment.config.property("gtfs.calendarDatesFile").getString()
    val routesFile = environment.config.property("gtfs.routesFile").getString()
    val shapesFile = environment.config.property("gtfs.shapesFile").getString()
    val stopsFile = environment.config.property("gtfs.stopsFile").getString()
    val stopTimesFile = environment.config.property("gtfs.stopTimesFile").getString()
    val tripsFile = environment.config.property("gtfs.tripsFile").getString()
    val feedInfoFile = environment.config.property("gtfs.feedInfoFile").getString()
    val outputDir = environment.config.property("gtfs.outputDir").getString()

    appConfig.gtfsConfig = GtfsConfig(
        url = gtfsUrl,
        gtfsFile = gtfsFile,
        agencyFile = agencyFile,
        calendarFile = calendarFile,
        calendarDatesFile = calendarDatesFile,
        routesFile = routesFile,
        shapesFile = shapesFile,
        stopsFile = stopsFile,
        stopTimesFile = stopTimesFile,
        tripsFile = tripsFile,
        feedInfoFile = feedInfoFile,
        outputDir = outputDir
    )
}

