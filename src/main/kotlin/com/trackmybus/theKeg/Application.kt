package com.trackmybus.theKeg

import com.trackmybus.theKeg.config.configureHTTP
import com.trackmybus.theKeg.config.configureMonitoring
import com.trackmybus.theKeg.config.setupConfig
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.di.configureKoin
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain
        .main(args)
}

fun Application.module() {
    configureKoin()
    configureHTTP()
    configureMonitoring()
    setupConfig()
    configureDatabases()
    configureSerialization()
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            },
        )
    }
}

fun Application.configureDatabases() {
    val databaseFactory by inject<DatabaseFactory>()
    databaseFactory.connect()
}
