package com.trackmybus.features.v1.data.local

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.data.local.dao.agency.AgenciesDao
import com.trackmybus.theKeg.features.v1.data.local.dao.calendar.CalendarDao
import com.trackmybus.theKeg.features.v1.data.local.dao.route.RouteDao
import com.trackmybus.theKeg.features.v1.data.local.dao.shape.ShapeDao
import com.trackmybus.theKeg.features.v1.data.local.dao.trip.TripDao
import com.trackmybus.theKeg.features.v1.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.domain.model.Agency
import com.trackmybus.theKeg.features.v1.domain.model.Calendar
import com.trackmybus.theKeg.features.v1.domain.model.Route
import com.trackmybus.theKeg.features.v1.domain.model.Shape
import com.trackmybus.theKeg.features.v1.domain.model.Trip
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

class TripDaoTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var tripDao: TripDao
    private lateinit var calendarDao: CalendarDao
    private lateinit var routeDao: RouteDao
    private lateinit var agenciesDao: AgenciesDao
    private lateinit var shapeDao: ShapeDao

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        tripDao = get()
        calendarDao = get()
        routeDao = get()
        agenciesDao = get()
        shapeDao = get()
        databaseFactory.connect()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new trip`() =
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
            val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1")
            val result = tripDao.addTrip(newTrip)
            assertTrue(result.isSuccess)
            assertNotNull(result.getOrNull())
            assertEquals(newTrip, result.getOrNull()?.toModel())
        }

    @Test
    fun `Get trip by existing ID`() =
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
            val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1")
            tripDao.addTrip(newTrip)
            val result = tripDao.getTripById("trip1")
            assertTrue(result.isSuccess)
            assertNotNull(result.getOrNull())
            assertEquals(newTrip, result.getOrNull()?.toModel())
        }

    @Test
    fun `Update existing trip`() =
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
            val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1")
            tripDao.addTrip(newTrip)
            val updatedTrip = Trip("route1", "1", "trip1", "newHeadsign", "newShortName", 1, "blockId", "shape1")
            val result = tripDao.updateTrip(updatedTrip)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Delete trip by existing ID`() =
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
            val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shape1")
            tripDao.addTrip(newTrip)
            val result = tripDao.deleteTrip("trip1")
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Delete trip by non-existent ID`() =
        runBlocking {
            val result = tripDao.deleteTrip("nonExistentId")
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}
