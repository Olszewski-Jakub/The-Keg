package com.trackmybus.config

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.config.AppConfig
import com.trackmybus.theKeg.config.setupConfig
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.testApplication
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.assertFalse

class AppConfigTest : KoinTest {
    private lateinit var appConfig: AppConfig

    @Before
    fun setUp() =
        testApplication {
            configureKoinUnitTest()
            application {
                (environment.config as MapApplicationConfig).apply {
                    put("ktor.server.isProd", "false")
                    put("postgres.driverClass", "org.postgresql.Driver")
                    put("postgres.jdbcURL", "jdbc:postgresql://localhost:5432/mydb")
                    put("postgres.database", "mydb")
                    put("postgres.user", "user")
                    put("postgres.password", "password")
                    put("postgres.maxPoolSize", "10")
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
            }
            appConfig = get()
        }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun setupConfig_validConfig_setsServerConfig() = assertFalse(appConfig.serverConfig.isProd)

    @Test
    fun setupConfig_validConfig_setsPostgresConfig() =
        with(appConfig.postgresConfig) {
            assertEquals("org.postgresql.Driver", driverClass)
            assertEquals("jdbc:postgresql://localhost:5432/mydb", jdbcUrl)
            assertEquals("mydb", database)
            assertEquals("user", user)
            assertEquals("password", password)
            assertEquals(10, maxPoolSize)
            assertTrue(autoCommit)
        }

    @Test
    fun setupConfig_validConfig_setsGtfsConfig() =

        with(appConfig.gtfsConfig) {
            assertEquals("http://example.com/gtfs.zip", url)
            assertEquals("gtfs.zip", gtfsFile)
            assertEquals("agency.txt", agencyFile)
            assertEquals("calendar.txt", calendarFile)
            assertEquals("calendar_dates.txt", calendarDatesFile)
            assertEquals("routes.txt", routesFile)
            assertEquals("shapes.txt", shapesFile)
            assertEquals("stops.txt", stopsFile)
            assertEquals("stop_times.txt", stopTimesFile)
            assertEquals("trips.txt", tripsFile)
            assertEquals("feed_info.txt", feedInfoFile)
            assertEquals("output", outputDir)
        }
}
