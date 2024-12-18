package com.trackmybus.database

import com.trackmybus.config.AppConfig

import java.sql.Connection
import java.sql.DriverManager

class DatabaseFactoryImpl(appConfig: AppConfig): DatabaseFactory {

    private val postgresConfig = appConfig.postgresConfig
    private val isProd = appConfig.serverConfig.isProd
    override lateinit var connection: Connection

    override fun connect() {
        Class.forName(postgresConfig.driverClass)
        if (isProd) {
            connection = DriverManager.getConnection(postgresConfig.url, postgresConfig.user, postgresConfig.password)
        } else {
            connection =  DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
        }
    }

    override fun close() {
        // used only on Unit tests
    }
}