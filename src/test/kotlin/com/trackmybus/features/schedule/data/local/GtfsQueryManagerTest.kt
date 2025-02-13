package com.trackmybus.features.schedule.data.local

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.data.local.dao.agency.AgenciesDao
import com.trackmybus.theKeg.features.v1.data.local.dao.calendar.CalendarDao
import com.trackmybus.theKeg.features.v1.data.local.dao.route.RouteDao
import com.trackmybus.theKeg.features.v1.data.local.dao.shape.ShapeDao
import com.trackmybus.theKeg.features.v1.data.local.dao.stop.StopDao
import com.trackmybus.theKeg.features.v1.data.local.dao.stopTime.StopTimeDao
import com.trackmybus.theKeg.features.v1.data.local.dao.trip.TripDao
import com.trackmybus.theKeg.features.v1.data.local.manager.GtfsQueryManager
import com.trackmybus.theKeg.features.v1.domain.model.Agency
import com.trackmybus.theKeg.features.v1.domain.model.Calendar
import com.trackmybus.theKeg.features.v1.domain.model.Route
import com.trackmybus.theKeg.features.v1.domain.model.Shape
import com.trackmybus.theKeg.features.v1.domain.model.Stop
import com.trackmybus.theKeg.features.v1.domain.model.StopTime
import com.trackmybus.theKeg.features.v1.domain.model.Trip
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get

class GtfsQueryManagerTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var gtfsQueryManager: GtfsQueryManager
    private lateinit var tripDao: TripDao
    private lateinit var calendarDao: CalendarDao
    private lateinit var routeDao: RouteDao
    private lateinit var agenciesDao: AgenciesDao
    private lateinit var shapeDao: ShapeDao
    private lateinit var stopDao: StopDao
    private lateinit var stopTimeDao: StopTimeDao

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        tripDao = get()
        calendarDao = get()
        routeDao = get()
        agenciesDao = get()
        shapeDao = get()
        gtfsQueryManager = get()
        shapeDao = get()
        stopDao = get()
        stopTimeDao = get()
        databaseFactory.connect()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun getStopsForGivenRoute_returnsCorrectStops() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
            tripDao.addTrip(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopDao.addStop(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopDao.addStop(Stop("stop2", 1, "Stop 2", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopDao.addStop(Stop("stop3", 1, "Stop 3", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopDao.addStop(Stop("stop4", 1, "Stop 4", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopDao.addStop(Stop("stop5", 1, "Stop 5", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeDao.addStopTime(
                StopTime(
                    1,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop1",
                    1,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    2,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop2",
                    2,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    3,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop3",
                    3,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    4,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop4",
                    4,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    5,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop5",
                    5,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )

            val result = gtfsQueryManager.getStopsForGivenRoute("route1", "stop1", "stop5")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(5, stops?.size)
        }

    @Test
    fun getDepartureTimesForRouteAndStop_returnsCorrectTimes() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
            tripDao.addTrip(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopDao.addStop(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeDao.addStopTime(
                StopTime(
                    1,
                    "trip1",
                    LocalTime(9, 0),
                    LocalTime(9, 0),
                    "stop1",
                    1,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    2,
                    "trip1",
                    LocalTime(10, 0),
                    LocalTime(10, 0),
                    "stop2",
                    2,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    3,
                    "trip1",
                    LocalTime(11, 0),
                    LocalTime(11, 0),
                    "stop3",
                    3,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )

            val result = gtfsQueryManager.getDepartureTimesForRouteAndStop("2023-10-10", "route1", "stop1")
            assertTrue(result.isSuccess)
            val times = result.getOrNull()
            assertEquals(1, times?.size)
        }

    @Test
    fun getStopsForTrip_returnsCorrectStops() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
            tripDao.addTrip(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopDao.addStop(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopDao.addStop(Stop("stop2", 1, "Stop 2", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopDao.addStop(Stop("stop3", 1, "Stop 3", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopDao.addStop(Stop("stop4", 1, "Stop 4", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopDao.addStop(Stop("stop5", 1, "Stop 5", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeDao.addStopTime(
                StopTime(
                    1,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop1",
                    1,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    2,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop2",
                    2,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    3,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop3",
                    3,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    4,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop4",
                    4,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    5,
                    "trip1",
                    LocalTime(12, 12),
                    LocalTime(13, 13),
                    "stop5",
                    5,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )

            val result = gtfsQueryManager.getStopsForTrip("trip1")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(5, stops?.size)
        }

    @Test
    fun getStopsForGivenRoute_withInvalidRoute_returnsEmptyList() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
            tripDao.addTrip(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopDao.addStop(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeDao.addStopTime(
                StopTime(
                    1,
                    "trip1",
                    LocalTime(9, 0),
                    LocalTime(9, 0),
                    "stop1",
                    1,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    2,
                    "trip1",
                    LocalTime(10, 0),
                    LocalTime(10, 0),
                    "stop2",
                    2,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    3,
                    "trip1",
                    LocalTime(11, 0),
                    LocalTime(11, 0),
                    "stop3",
                    3,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )

            val result = gtfsQueryManager.getStopsForGivenRoute("invalidRoute", "stop1", "stop5")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(0, stops?.size)
        }

    @Test
    fun getStopsForTrip_withInvalidTripId_returnsEmptyList() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
            tripDao.addTrip(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopDao.addStop(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeDao.addStopTime(
                StopTime(
                    1,
                    "trip1",
                    LocalTime(9, 0),
                    LocalTime(9, 0),
                    "stop1",
                    1,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    2,
                    "trip1",
                    LocalTime(10, 0),
                    LocalTime(10, 0),
                    "stop2",
                    2,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    3,
                    "trip1",
                    LocalTime(11, 0),
                    LocalTime(11, 0),
                    "stop3",
                    3,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )

            val result = gtfsQueryManager.getStopsForTrip("invalidTripId")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(0, stops?.size)
        }

    @Test
    fun getRoutesPassingThroughStop_withInvalidStopId_returnsEmptyList() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
            routeDao.addRoute(Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor"))
            routeDao.addRoute(Route("route2", "2", "Route 2", "shortName", 1, "desc", "url", "color", "textColor"))
            stopDao.addStop(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeDao.addStopTime(
                StopTime(
                    1,
                    "trip1",
                    LocalTime(9, 0),
                    LocalTime(9, 0),
                    "stop1",
                    1,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    2,
                    "trip2",
                    LocalTime(10, 0),
                    LocalTime(10, 0),
                    "stop2",
                    2,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )

            val result = gtfsQueryManager.getRoutesPassingThroughStop("invalidStopId")
            assertTrue(result.isSuccess)
            val routes = result.getOrNull()
            assertEquals(0, routes?.size)
        }

    @Test
    fun getFirstAndLastStopsForRoute_withInvalidRouteId_returnsEmptyList() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
            tripDao.addTrip(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopDao.addStop(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopDao.addStop(Stop("stop5", 1, "Stop 5", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeDao.addStopTime(
                StopTime(
                    1,
                    "trip1",
                    LocalTime(9, 0),
                    LocalTime(9, 0),
                    "stop1",
                    1,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )
            stopTimeDao.addStopTime(
                StopTime(
                    2,
                    "trip2",
                    LocalTime(10, 0),
                    LocalTime(10, 0),
                    "stop5",
                    2,
                    "Headsign",
                    1,
                    1,
                    1,
                ),
            )

            val result = gtfsQueryManager.getFirstAndLastStopsForRoute("invalidRouteId")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(0, stops?.size)
        }
}
