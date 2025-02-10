package com.trackmybus.database

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.config.AppConfig
import com.trackmybus.theKeg.config.setupConfig
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.database.DatabaseFactoryImpl
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.testApplication
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertNotNull

class DatabaseFactoryTest : KoinTest {
    private lateinit var appConfig: AppConfig
    private lateinit var databaseFactory: DatabaseFactory

    @Before
    fun setUp() =
        testApplication {
            configureKoinUnitTest()

            application {
                (environment.config as MapApplicationConfig).apply {
                    put("ktor.server.isProd", "false")
                    put("postgres.driverClass", "org.h2.Driver")
                    put("postgres.jdbcURL", "jdbc:h2:mem:;DATABASE_TO_UPPER=false;MODE=MYSQL")
                    put("postgres.database", "")
                    put("postgres.user", "")
                    put("postgres.password", "")
                    put("postgres.maxPoolSize", "1")
                    put("postgres.autoCommit", "true")

                    put("gtfs.url", "http://example.com/gtfs.zip")
                    put("gtfs.gtfsFile", "gtfs.zip")
                    put("gtfs.agencyFile", "agency.txt")
                    put("gtfs.calendarFile", "calendar.txt")
                    put("gtfs.calendarDatesFile", "calendar_dates.txt")
                    put("gtfs.routesFile", "routes.txt")
                    put("gtfs.shapesFile", "shapes.txt")
                    put("gtfs.stopsFile", "stops.txt")
                    put("gtfs.stopTimesFile", "stop_times.txt")
                    put("gtfs.tripsFile", "trips.txt")
                    put("gtfs.feedInfoFile", "feed_info.txt")
                    put("gtfs.outputDir", "output")
                }
                setupConfig()
                appConfig = get()
                databaseFactory =
                    DatabaseFactoryImpl(LoggerFactory.getLogger(DatabaseFactoryImpl::class.java), get(), get())
                databaseFactory.connect()
            }
        }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun connect_initializesDatabase() {
        assertNotNull(databaseFactory.database)
    }

    @Test
    fun close_closesConnectionPool() {
        databaseFactory.close()
    }

    @Test
    fun dbQuery_executesSuspendedTransaction() =
        runBlocking {
            val result = databaseFactory.dbQuery { "test" }
            assert(result == "test")
        }

    @Test
    fun dbQuery_executesBlock() =
        runBlocking {
            val block = mockk<suspend () -> String>(relaxed = true)
            databaseFactory.dbQuery { block() }
            coVerify { block() }
        }
}
