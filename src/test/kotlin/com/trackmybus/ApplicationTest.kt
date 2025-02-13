package com.trackmybus

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.config.configureRouting
import com.trackmybus.theKeg.config.setupConfig
import com.trackmybus.theKeg.configureDatabases
import com.trackmybus.theKeg.configureSerialization
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.module
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.testApplication
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory

    @Before
    fun setUp() =
        testApplication {
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
            configureKoinUnitTest()

            databaseFactory = get<DatabaseFactory>()
            databaseFactory.connect()
        }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun databaseConnectionEstablished() = testApplication {
        application { configureDatabases() }
        assertTrue(databaseFactory.database.url.isNotEmpty())
    }

    @Test
    fun routingConfiguredCorrectly() = testApplication {
        application { configureRouting() }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}