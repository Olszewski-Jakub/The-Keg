package com.trackmybus.features.v1.schedule.data.local

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.agency.AgenciesDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.calendar.CalendarDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.route.RouteDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.shape.ShapeDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.stop.StopDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.stopTime.StopTimeDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.trip.TripDao
import com.trackmybus.theKeg.features.v1.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Agency
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Calendar
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Route
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Shape
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Stop
import com.trackmybus.theKeg.features.v1.schedule.domain.model.StopTime
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Trip
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get

class StopTimeDaoTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var stopTimeDao: StopTimeDao
    private lateinit var tripDao: TripDao
    private lateinit var stopDao: StopDao
    private lateinit var calendarDao: CalendarDao
    private lateinit var routeDao: RouteDao
    private lateinit var agenciesDao: AgenciesDao
    private lateinit var shapeDao: ShapeDao

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        databaseFactory.connect()
        stopTimeDao = get()
        tripDao = get()
        stopDao = get()
        calendarDao = get()
        routeDao = get()
        agenciesDao = get()
        shapeDao = get()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new stop time`() {
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shapeId", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
        }
        val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shapeId")
        val newStop = Stop("stop1", 1, "Stop 1", 1.0, 1.0, "Stop 1 Description", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { tripDao.addTrip(newTrip) }
        runBlocking { stopDao.addStop(newStop) }
        val newStopTime = StopTime(1, "trip1", LocalTime(12, 0, 0), LocalTime(12, 5, 0), "stop1", 1, "Stop Headsign", 1, 1, 1)
        runBlocking { stopTimeDao.addStopTime(newStopTime) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
            assertEquals(newStopTime, this.getOrNull()?.toModel())
        }
    }

    @Test
    fun `Add stop time with existing ID`() {
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shapeId", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
        }
        val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shapeId")
        val newStop = Stop("stop1", 1, "Stop 1", 1.0, 1.0, "Stop 1 Description", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { tripDao.addTrip(newTrip) }
        runBlocking { stopDao.addStop(newStop) }
        val newStopTime = StopTime(1, "trip1", LocalTime(12, 0, 0), LocalTime(12, 5, 0), "stop1", 1, "Stop Headsign", 1, 1, 1)
        runBlocking { stopTimeDao.addStopTime(newStopTime) }
        val stopTimeWithExistingId = StopTime(1, "trip2", LocalTime(13, 0, 0), LocalTime(13, 5, 0), "stop2", 2, "Stop Headsign 2", 2, 2, 2)
        runBlocking { stopTimeDao.addStopTime(stopTimeWithExistingId) }.run {
            assertTrue(this.isFailure)
            assertNull(this.getOrNull())
        }
    }

    @Test
    fun `Get list of all stop times`() =
        runBlocking {
            runBlocking {
                val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
                agenciesDao.addAgency(newAgency)
                val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
                val newCalendar =
                    Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
                val newShape = Shape(1, "shapeId", 0.0, 0.0, 1, 0.0)
                routeDao.addRoute(newRoute)
                calendarDao.addCalendar(newCalendar)
                shapeDao.addShape(newShape)
            }
            val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shapeId")
            val newStop = Stop("stop1", 1, "Stop 1", 1.0, 1.0, "Stop 1 Description", "Zone 1", "Stop URL", 1, "Parent Station")
            runBlocking { tripDao.addTrip(newTrip) }
            runBlocking { stopDao.addStop(newStop) }
            val newStopTime = StopTime(1, "trip1", LocalTime(12, 0, 0), LocalTime(12, 5, 0), "stop1", 1, "Stop Headsign", 1, 1, 1)
            runBlocking { stopTimeDao.addStopTime(newStopTime) }
            val stopTimes = stopTimeDao.getAllStopTimes()
            assertTrue(stopTimes.isSuccess)
            assertNotNull(stopTimes.getOrNull())
        }

    @Test
    fun `Get stop time with existing ID`() {
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shapeId", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
        }
        val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shapeId")
        val newStop = Stop("stop1", 1, "Stop 1", 1.0, 1.0, "Stop 1 Description", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { tripDao.addTrip(newTrip) }
        runBlocking { stopDao.addStop(newStop) }
        val newStopTime = StopTime(1, "trip1", LocalTime(12, 0, 0), LocalTime(12, 5, 0), "stop1", 1, "Stop Headsign", 1, 1, 1)
        runBlocking { stopTimeDao.addStopTime(newStopTime) }
        runBlocking { stopTimeDao.getStopTimeById(1) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Get stop time with ID that does not exist`() =
        runBlocking {
            val stopTime = stopTimeDao.getStopTimeById(-1)
            assertTrue(stopTime.isSuccess)
            assertNull(stopTime.getOrNull())
        }

    @Test
    fun `Update existing stop time`() =
        runBlocking {
            runBlocking {
                val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
                agenciesDao.addAgency(newAgency)
                val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
                val newCalendar =
                    Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
                val newShape = Shape(1, "shapeId", 0.0, 0.0, 1, 0.0)
                routeDao.addRoute(newRoute)
                calendarDao.addCalendar(newCalendar)
                shapeDao.addShape(newShape)
            }
            val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shapeId")
            val newStop = Stop("stop1", 1, "Stop 1", 1.0, 1.0, "Stop 1 Description", "Zone 1", "Stop URL", 1, "Parent Station")
            runBlocking { tripDao.addTrip(newTrip) }
            runBlocking { stopDao.addStop(newStop) }
            val newStopTime = StopTime(1, "trip1", LocalTime(12, 0, 0), LocalTime(12, 5, 0), "stop1", 1, "Stop Headsign", 1, 1, 1)
            runBlocking { stopTimeDao.addStopTime(newStopTime) }
            val updatedStopTime =
                StopTime(1, "trip1", LocalTime(12, 10, 0), LocalTime(12, 15, 0), "stop1", 1, "Updated Stop Headsign", 1, 1, 1)
            val result = stopTimeDao.updateStopTime(updatedStopTime)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Update stop time with non-existent ID`() =
        runBlocking {
            val updatedStopTime =
                StopTime(1, "trip1", LocalTime(12, 10, 0), LocalTime(12, 15, 0), "stop1", 1, "Updated Stop Headsign", 1, 1, 1)
            val result = stopTimeDao.updateStopTime(updatedStopTime)
            assertTrue(result.isFailure)
        }

    @Test
    fun `Delete stop time with existing ID`() {
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            agenciesDao.addAgency(newAgency)
            val newRoute = Route("route1", "1", "Route 1", "shortName", 1, "desc", "url", "color", "textColor")
            val newCalendar =
                Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            val newShape = Shape(1, "shapeId", 0.0, 0.0, 1, 0.0)
            routeDao.addRoute(newRoute)
            calendarDao.addCalendar(newCalendar)
            shapeDao.addShape(newShape)
        }
        val newTrip = Trip("route1", "1", "trip1", "headsign", "shortName", 1, "blockId", "shapeId")
        val newStop = Stop("stop1", 1, "Stop 1", 1.0, 1.0, "Stop 1 Description", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { tripDao.addTrip(newTrip) }
        runBlocking { stopDao.addStop(newStop) }
        val newStopTime = StopTime(1, "trip1", LocalTime(12, 0, 0), LocalTime(12, 5, 0), "stop1", 1, "Stop Headsign", 1, 1, 1)
        runBlocking { stopTimeDao.addStopTime(newStopTime) }
        runBlocking { stopTimeDao.deleteStopTime(1) }.run {
            assertTrue(isSuccess)
            assertTrue(getOrNull()!!)
        }
    }

    @Test
    fun `Delete stop time with non-existing ID`() =
        runBlocking {
            val result = stopTimeDao.deleteStopTime(1)
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}
