package com.trackmybus.config

import io.ktor.server.application.Application
import io.ktor.server.application.log
import org.koin.ktor.ext.inject

data class ServerConfig(
    val isProd: Boolean
)

data class PostgresConfig(
    val driverClass: String,
    val url: String,
    val user: String,
    val password: String
)

class AppConfig {
    lateinit var serverConfig: ServerConfig
    lateinit var postgresConfig: PostgresConfig
}

fun Application.setupConfig() {
    val appConfig by inject<AppConfig>()

    // Server
    val serverObject = environment.config.config("ktor.server")
    val isProd = serverObject.property("isProd").getString().toBoolean()
    appConfig.serverConfig = ServerConfig(isProd)

    // Postgres
    val driverClass = environment.config.property("postgres.driverClass").getString()
    val url = environment.config.property("postgres.url").getString()
    val user = environment.config.property("postgres.user").getString()
    val password = environment.config.property("postgres.password").getString()

    appConfig.postgresConfig = PostgresConfig(driverClass = driverClass, url = url, user = user, password = password)
}

