package com.trackmybus.features.schedule.data.local

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.dao.calendar.CalendarDao
import com.trackmybus.theKeg.features.schedule.data.local.dao.calendarDate.CalendarDatesDao
import com.trackmybus.theKeg.features.schedule.domain.model.Calendar
import com.trackmybus.theKeg.features.schedule.domain.model.CalendarDate
import com.trackmybus.theKeg.features.schedule.domain.model.ScheduleAdjustmentType
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertNotNull

class CalendarDateDaoTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var calendarDatesDao: CalendarDatesDao
    private lateinit var calendarDao: CalendarDao

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        calendarDatesDao = get()
        calendarDao = get()
        databaseFactory.connect()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new calendar date`() {
        val newCaledar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarDao.addCalendar(newCaledar) }
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDatesDao.addCalendarDate(newCalendarDate) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Add calendar date with existing ID`() {
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDatesDao.addCalendarDate(newCalendarDate) }
        val calendarDateWithExistingId =
            CalendarDate(1, "1", LocalDate(2024, 1, 1), ScheduleAdjustmentType.SERVICE_REMOVED)
        runBlocking { calendarDatesDao.addCalendarDate(calendarDateWithExistingId) }.run {
            assertTrue(this.isFailure)
            assertNull(this.getOrNull())
        }
    }

    @Test
    fun `Get list of all calendar dates`() {
        runBlocking {
            val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
            runBlocking { calendarDatesDao.addCalendarDate(newCalendarDate) }
            val calendarDates = calendarDatesDao.getAllCalendarDates()
            assertTrue(calendarDates.isSuccess)
            assertNotNull(calendarDates.getOrNull())
        }
    }

    @Test
    fun `Get calendar date with existing ID`() {
        val newCaledar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarDao.addCalendar(newCaledar) }
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDatesDao.addCalendarDate(newCalendarDate) }
        runBlocking { calendarDatesDao.getCalendarDateById(1) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Get calendar date with ID that does not exist`() = runBlocking {
        val calendarDate = calendarDatesDao.getCalendarDateById(-1)
        assertTrue(calendarDate.isSuccess)
        assertNull(calendarDate.getOrNull())
    }

    @Test
    fun `Update existing calendar date`() = runBlocking {
        val newCaledar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarDao.addCalendar(newCaledar) }
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDatesDao.addCalendarDate(newCalendarDate) }
        val updatedCalendarDate = CalendarDate(1, "1", LocalDate(2024, 1, 1), ScheduleAdjustmentType.SERVICE_REMOVED)
        val result = calendarDatesDao.updateCalendarDate(updatedCalendarDate)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Update calendar date with non-existent ID`() = runBlocking {
        val updatedCalendarDate = CalendarDate(1, "1", LocalDate(2024, 1, 1), ScheduleAdjustmentType.SERVICE_REMOVED)
        val result = calendarDatesDao.updateCalendarDate(updatedCalendarDate)
        assertTrue(result.isFailure)
    }

    @Test
    fun `Delete calendar date with existing ID`() {
        val newCaledar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarDao.addCalendar(newCaledar) }
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDatesDao.addCalendarDate(newCalendarDate) }
        runBlocking { calendarDatesDao.deleteCalendarDate(1) }.run {
            assertTrue(isSuccess)
            assertTrue(getOrNull()!!)
        }
    }

    @Test
    fun `Delete calendar date with non-existing ID`() = runBlocking {
        val newCaledar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarDao.addCalendar(newCaledar) }
        val result = calendarDatesDao.deleteCalendarDate(1)
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }
}