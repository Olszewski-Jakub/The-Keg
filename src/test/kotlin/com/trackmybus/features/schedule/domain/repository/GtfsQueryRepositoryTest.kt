package com.trackmybus.features.schedule.domain.repository

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Agency
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Calendar
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Route
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Shape
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Stop
import com.trackmybus.theKeg.features.v1.schedule.domain.model.StopTime
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Trip
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.agency.AgencyRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.calendar.CalendarRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.gtfsQueryRepository.GtfsQueryRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.route.RouteRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.shape.ShapeRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.stop.StopRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.stopTime.StopTimeRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.trip.TripRepository
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

class GtfsQueryRepositoryTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var gtfsQueryRepository: GtfsQueryRepository
    private lateinit var tripRepository: TripRepository
    private lateinit var calendarRepository: CalendarRepository
    private lateinit var routeRepository: RouteRepository
    private lateinit var agenciesRepository: AgencyRepository
    private lateinit var shapeRepository: ShapeRepository
    private lateinit var stopRepository: StopRepository
    private lateinit var stopTimeRepository: StopTimeRepository

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        tripRepository = get()
        calendarRepository = get()
        routeRepository = get()
        agenciesRepository = get()
        shapeRepository = get()
        gtfsQueryRepository = get()
        shapeRepository = get()
        stopRepository = get()
        stopTimeRepository = get()
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
            agenciesRepository.add(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeRepository.add(newRoute)
            calendarRepository.add(newCalendar)
            shapeRepository.add(newShape)
            tripRepository.add(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopRepository.add(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopRepository.add(Stop("stop2", 1, "Stop 2", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopRepository.add(Stop("stop3", 1, "Stop 3", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopRepository.add(Stop("stop4", 1, "Stop 4", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopRepository.add(Stop("stop5", 1, "Stop 5", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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

            val result = gtfsQueryRepository.getStopsForGivenRoute("route1", "stop1", "stop5")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(5, stops?.size)
        }

    @Test
    fun getStopsForTrip_returnsCorrectStops() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesRepository.add(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeRepository.add(newRoute)
            calendarRepository.add(newCalendar)
            shapeRepository.add(newShape)
            tripRepository.add(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopRepository.add(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopRepository.add(Stop("stop2", 1, "Stop 2", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopRepository.add(Stop("stop3", 1, "Stop 3", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopRepository.add(Stop("stop4", 1, "Stop 4", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopRepository.add(Stop("stop5", 1, "Stop 5", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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

            val result = gtfsQueryRepository.getStopsForTrip("trip1")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(5, stops?.size)
        }

    @Test
    fun getStopsForGivenRoute_withInvalidRoute_returnsEmptyList() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesRepository.add(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeRepository.add(newRoute)
            calendarRepository.add(newCalendar)
            shapeRepository.add(newShape)
            tripRepository.add(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopRepository.add(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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

            val result = gtfsQueryRepository.getStopsForGivenRoute("invalidRoute", "stop1", "stop5")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(0, stops?.size)
        }

    @Test
    fun getStopsForTrip_withInvalidTripId_returnsEmptyList() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesRepository.add(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeRepository.add(newRoute)
            calendarRepository.add(newCalendar)
            shapeRepository.add(newShape)
            tripRepository.add(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopRepository.add(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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

            val result = gtfsQueryRepository.getStopsForTrip("invalidTripId")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(0, stops?.size)
        }

    @Test
    fun getRoutesPassingThroughStop_withInvalidStopId_returnsEmptyList() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesRepository.add(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeRepository.add(newRoute)
            calendarRepository.add(newCalendar)
            shapeRepository.add(newShape)
            routeRepository.add(Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor"))
            routeRepository.add(Route("route2", "2", "Route 2", "shortName", 1, "desc", "url", "color", "textColor"))
            stopRepository.add(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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

            val result = gtfsQueryRepository.getRoutesPassingThroughStop("invalidStopId")
            assertTrue(result.isSuccess)
            val routes = result.getOrNull()
            assertEquals(0, routes?.size)
        }

    @Test
    fun getFirstAndLastStopsForRoute_withInvalidRouteId_returnsEmptyList() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesRepository.add(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
            routeRepository.add(newRoute)
            calendarRepository.add(newCalendar)
            shapeRepository.add(newShape)
            tripRepository.add(Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1"))
            stopRepository.add(Stop("stop1", 1, "Stop 1", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopRepository.add(Stop("stop5", 1, "Stop 5", 0.0, 0.0, "desc", "zone1", "url", 0, ""))
            stopTimeRepository.add(
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
            stopTimeRepository.add(
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

            val result = gtfsQueryRepository.getFirstAndLastStopsForRoute("invalidRouteId")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(0, stops?.size)
        }
}
