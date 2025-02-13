package com.trackmybus.features.schedule.domain.usecases

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.domain.model.Agency
import com.trackmybus.theKeg.features.v1.domain.model.Calendar
import com.trackmybus.theKeg.features.v1.domain.model.Route
import com.trackmybus.theKeg.features.v1.domain.model.Shape
import com.trackmybus.theKeg.features.v1.domain.model.Trip
import com.trackmybus.theKeg.features.v1.domain.repository.agency.AgencyRepository
import com.trackmybus.theKeg.features.v1.domain.repository.calendar.CalendarRepository
import com.trackmybus.theKeg.features.v1.domain.repository.route.RouteRepository
import com.trackmybus.theKeg.features.v1.domain.repository.shape.ShapeRepository
import com.trackmybus.theKeg.features.v1.domain.repository.trip.TripRepository
import com.trackmybus.theKeg.features.v1.domain.usecases.ScheduleUseCase
import com.trackmybus.theKeg.features.v1.resource.RouteType
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get

class ScheduleUseCaseTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var scheduleUseCase: ScheduleUseCase
    private lateinit var tripRepository: TripRepository
    private lateinit var calendarRepository: CalendarRepository
    private lateinit var routeRepository: RouteRepository
    private lateinit var agenciesRepository: AgencyRepository
    private lateinit var shapeRepository: ShapeRepository

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        tripRepository = get()
        calendarRepository = get()
        routeRepository = get()
        agenciesRepository = get()
        shapeRepository = get()
        scheduleUseCase = get()
        databaseFactory.connect()

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
            val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1")
            tripRepository.add(newTrip)
        }
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun getRouteDirections_returnsCorrectDirections() =
        runBlocking {
            val result = scheduleUseCase.getRouteDirections("route1")
            assertTrue(result.isSuccess)
            val directions = result.getOrNull()
            assertEquals(0, directions?.size)
        }

    @Test
    fun getStopsForGivenRoute_returnsCorrectStops() =
        runBlocking {
            val result = scheduleUseCase.getStopsForGivenRoute("route1", "stop1", "stop5")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(0, stops?.size)
        }

    @Test
    fun getAllRoutes_returnsAllRoutes() =
        runBlocking {
            val result = scheduleUseCase.getAllRoutes()
            assertTrue(result.isSuccess)
            val routes = result.getOrNull()
            assertEquals(1, routes?.size)
        }

    @Test
    fun getRouteByType_returnsCorrectRoutes() =
        runBlocking {
            val result = scheduleUseCase.getRouteByType(RouteType.UNDERGROUND)
            assertTrue(result.isSuccess)
            val routes = result.getOrNull()
            assertEquals(1, routes?.size)
        }

    @Test
    fun getStopsForTrip_returnsCorrectStops() =
        runBlocking {
            val result = scheduleUseCase.getStopsForTrip("trip1")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(0, stops?.size)
        }

    @Test
    fun getRoutesPassingThroughStop_returnsCorrectRoutes() =
        runBlocking {
            val result = scheduleUseCase.getRoutesPassingThroughStop("stop1")
            assertTrue(result.isSuccess)
            val routes = result.getOrNull()
            assertEquals(0, routes?.size)
        }

    @Test
    fun getStopsForGivenRoute_withInvalidRoute_returnsEmptyList() =
        runBlocking {
            val result = scheduleUseCase.getStopsForGivenRoute("invalidRoute", "stop1", "stop5")
            assertTrue(result.isSuccess)
            val stops = result.getOrNull()
            assertEquals(0, stops?.size)
        }

    @Test
    fun getRoutesPassingThroughStop_withInvalidStopId_returnsEmptyList() =
        runBlocking {
            val result = scheduleUseCase.getRoutesPassingThroughStop("invalidStopId")
            assertTrue(result.isSuccess)
            val routes = result.getOrNull()
            assertEquals(0, routes?.size)
        }

    @Test
    fun getShapesForTrip_withInvalidTripId_returnsEmptyList() =
        runBlocking {
            val result = scheduleUseCase.getShapesForTrip("invalidTripId")
            assertTrue(result.isSuccess)
            val shapes = result.getOrNull()
            assertEquals(0, shapes?.size)
        }
}
