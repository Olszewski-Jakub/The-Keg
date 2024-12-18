package com.trackmybus

import com.trackmybus.config.configureHTTP
import com.trackmybus.config.configureMonitoring
import com.trackmybus.config.setupConfig
import com.trackmybus.database.DatabaseFactory
import com.trackmybus.di.configureKoin
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureHTTP()
    configureMonitoring()
    setupConfig()
    configureDatabases()
    configureRouting()
}

fun Application.configureDatabases() {
    val databaseFactory by inject<DatabaseFactory>()
    databaseFactory.connect()
}