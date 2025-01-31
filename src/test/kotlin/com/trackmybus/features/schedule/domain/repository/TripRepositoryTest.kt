package com.trackmybus.features.schedule.domain.repository

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.schedule.domain.model.Agency
import com.trackmybus.theKeg.features.schedule.domain.model.Calendar
import com.trackmybus.theKeg.features.schedule.domain.model.Route
import com.trackmybus.theKeg.features.schedule.domain.model.Shape
import com.trackmybus.theKeg.features.schedule.domain.model.Trip
import com.trackmybus.theKeg.features.schedule.domain.repository.agency.AgencyRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.calendar.CalendarRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.route.RouteRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.shape.ShapeRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.trip.TripRepository
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get

class TripRepositoryTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
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
        databaseFactory.connect()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new trip`() = runBlocking {
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
        val result = tripRepository.add(newTrip)
        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
        assertEquals(newTrip, result.getOrNull())
    }

    @Test
    fun `Get trip by existing ID`() = runBlocking {
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
        val result = tripRepository.getById("trip1")
        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
        assertEquals(newTrip, result.getOrNull())
    }

    @Test
    fun `Update existing trip`() = runBlocking {
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
        val updatedTrip = Trip("route1", "1", "trip1", "newHeadsign", "newShortName", 1, "blockId", "shape1")
        val result = tripRepository.update(updatedTrip)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Delete trip by existing ID`() = runBlocking {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        agenciesRepository.add(newAgency)
        val newRoute = Route("route1","1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
        val newCalendar =
            Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        val newShape = Shape(1, "shape1", 0.0, 0.0, 1, 0.0)
        routeRepository.add(newRoute)
        calendarRepository.add(newCalendar)
        shapeRepository.add(newShape)
        val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1")
        tripRepository.add(newTrip)
        val result = tripRepository.deleteById("trip1")
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Delete trip by non-existent ID`() = runBlocking {
        val result = tripRepository.deleteById("nonExistentId")
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }
}