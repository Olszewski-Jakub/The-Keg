package com.trackmybus.theKeg.database

import com.trackmybus.theKeg.config.AppConfig
import com.trackmybus.theKeg.features.schedule.data.local.ScheduleSchemaInitializer
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.util.logging.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject

class DatabaseFactoryImpl(private val logger: Logger, appConfig: AppConfig, private val scheduleSchemaInitializer: ScheduleSchemaInitializer) : DatabaseFactory {
    private val postgresConfig = appConfig.postgresConfig
    private val isProd = appConfig.serverConfig.isProd
    override lateinit var database: Database

    override fun connect() {
        runCatching {
            val connectionPool = createHikariDataSource(
                url = databaseUrlBuilder(
                    jdbcUrl = postgresConfig.jdbcUrl,
                    defaultDatabase = postgresConfig.database,
                    user = postgresConfig.user,
                    password = postgresConfig.password
                ),
                driver = postgresConfig.driverClass,
                maxPoolSize = postgresConfig.maxPoolSize,
                autoCommit = postgresConfig.autoCommit
            )
            database = Database.connect(connectionPool)
            transaction(database) {
                scheduleSchemaInitializer.initSchemas()
            }
        }.onSuccess {
            logger.info("Connected to database")
        }.onFailure {
            logger.error("Failed to connect to database", it)
        }
    }

    override fun close() {
        // used only on Unit tests
    }

    private fun createHikariDataSource(
        url: String, driver: String, maxPoolSize: Int, autoCommit: Boolean
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        maximumPoolSize = maxPoolSize
        isAutoCommit = autoCommit
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    private fun databaseUrlBuilder(jdbcUrl: String, defaultDatabase: String, user: String, password: String) =
        "$jdbcUrl/$defaultDatabase?user=$user&password=$password"

    override suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}