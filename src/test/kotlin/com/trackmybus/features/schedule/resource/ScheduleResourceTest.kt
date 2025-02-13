package com.trackmybus.features.schedule.resource

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.resource.scheduleRoutes
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals

class ScheduleResourceTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory

    @Before
    fun setUp() =
        testApplication {
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
    fun getRouteVariants_withValidRouteId_returnsDirections() =
        testApplication {
            install(Resources)
            application {
                routing {
                    scheduleRoutes()
                }
            }
            val response = client.get("/the-keg/v1/schedule/route/variants?routeId=route1")
            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("[]"))
        }

    @Test
    fun getStopList_withValidParams_returnsStops() =
        testApplication {
            install(Resources)
            application {
                routing {
                    scheduleRoutes()
                }
            }
            val response =
                client.get("/the-keg/v1/schedule/stops/list?routeId=route1&firstStopId=stop1&endStopId=stop5")
            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("[]"))
        }

    @Test
    fun getRouteByType_withValidType_returnsRoutes() =
        testApplication {
            install(Resources)
            application {
                routing {
                    scheduleRoutes()
                }
            }
            val response = client.get("/the-keg/v1/schedule/route/byType?type=UNDERGROUND")
            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("[]"))
        }

    @Test
    fun getAllRoutes_returnsAllRoutes() =
        testApplication {
            install(Resources)
            application {
                routing {
                    scheduleRoutes()
                }
            }
            val response = client.get("/the-keg/v1/schedule/route/all")
            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("[]"))
        }

    @Test
    fun getDepartureTimesFromStop_withValidParams_returnsTimes() =
        testApplication {
            install(Resources)
            application {
                routing {
                    scheduleRoutes()
                }
            }
            val response =
                client.get("/the-keg/v1/schedule/stops/departureTimes?stopId=stop1&routeId=route1&date=2023-01-01")
            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("[]"))
        }

    @Test
    fun getStopsForTrip_withValidTripId_returnsStops() =
        testApplication {
            install(Resources)
            application {
                routing {
                    scheduleRoutes()
                }
            }
            val response = client.get("/the-keg/v1/schedule/trip?tripId=trip1")
            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("[]"))
        }

    @Test
    fun getRoutesPassingThroughStop_withValidStopId_returnsRoutes() =
        testApplication {
            install(Resources)
            application {
                routing {
                    scheduleRoutes()
                }
            }
            val response = client.get("/the-keg/v1/schedule/stops/passingRoutes?stopId=stop1")
            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("[]"))
        }

    @Test
    fun getShapesForTrip_withValidTripId_returnsShapes() =
        testApplication {
            install(Resources)
            application {
                routing {
                    scheduleRoutes()
                }
            }
            val response = client.get("/the-keg/v1/schedule/trip/shapes?tripId=trip1")
            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue(response.bodyAsText().contains("[]"))
        }
}
