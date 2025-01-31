package com.trackmybus.database

import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.ScheduleSchemaInitializer
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactoryForUnitTest(private val scheduleSchemaInitializer: ScheduleSchemaInitializer) : DatabaseFactory {
    private lateinit var connectionPool: HikariDataSource

    override lateinit var database: Database

    override fun connect() {
        connectionPool = createHikariDataSource()
        database = Database.connect(connectionPool)
        transaction(database) {
            scheduleSchemaInitializer.initSchemas()
        }
    }

    override fun close() {
        connectionPool.close()
    }

    override suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun createHikariDataSource() = HikariDataSource(HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:;DATABASE_TO_UPPER=false;MODE=MYSQL"
        maximumPoolSize = 2
        isAutoCommit = true
        validate()
    })

}