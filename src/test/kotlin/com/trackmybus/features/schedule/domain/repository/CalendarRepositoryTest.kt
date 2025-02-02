package com.trackmybus.features.schedule.domain.repository

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.dao.calendar.CalendarDao
import com.trackmybus.theKeg.features.schedule.domain.model.Calendar
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CalendarRepositoryTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var calendarDao: CalendarDao

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        databaseFactory.connect()
        calendarDao = get()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new calendar`() {
        val newCalendar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarDao.addCalendar(newCalendar) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Add calendar with existing ID`() {
        val newCalendar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarDao.addCalendar(newCalendar) }
        val calendarWithExistingId =
            Calendar("1", false, false, false, false, false, false, false, LocalDate(2024, 1, 1), LocalDate(2024, 12, 31))
        runBlocking { calendarDao.addCalendar(calendarWithExistingId) }.run {
            assertTrue(this.isFailure)
            assertNull(this.getOrNull())
        }
    }

    @Test
    fun `Get list of all calendars`() =
        runBlocking {
            val newCalendar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            runBlocking { calendarDao.addCalendar(newCalendar) }
            val calendars = calendarDao.getAllCalendars()
            assertTrue(calendars.isSuccess)
        }

    @Test
    fun `Get calendar with existing ID`() {
        val newCalendar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarDao.addCalendar(newCalendar) }
        runBlocking { calendarDao.getCalendarById("1") }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Get calendar with ID that does not exist`() =
        runBlocking {
            val calendar = calendarDao.getCalendarById("-1")
            assertTrue(calendar.isSuccess)
            assertNull(calendar.getOrNull())
        }

    @Test
    fun `Update existing calendar`() =
        runBlocking {
            val newCalendar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
            runBlocking { calendarDao.addCalendar(newCalendar) }
            val updatedCalendar =
                Calendar("1", false, false, false, false, false, false, false, LocalDate(2024, 1, 1), LocalDate(2024, 12, 31))
            val result = calendarDao.updateCalendar(updatedCalendar)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Update calendar with non-existent ID`() =
        runBlocking {
            val updatedCalendar =
                Calendar("1", false, false, false, false, false, false, false, LocalDate(2024, 1, 1), LocalDate(2024, 12, 31))
            val result = calendarDao.updateCalendar(updatedCalendar)
            assertTrue(result.isFailure)
        }

    @Test
    fun `Delete calendar with existing ID`() {
        val newCalendar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarDao.addCalendar(newCalendar) }
        runBlocking { calendarDao.deleteCalendar("1") }.run {
            assertTrue(isSuccess)
            assertTrue(getOrNull()!!)
        }
    }

    @Test
    fun `Delete calendar with non-existing ID`() =
        runBlocking {
            val result = calendarDao.deleteCalendar("1")
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}
