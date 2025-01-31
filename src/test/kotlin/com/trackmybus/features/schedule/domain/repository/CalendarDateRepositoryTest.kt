package com.trackmybus.features.schedule.domain.repository

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.domain.model.Calendar
import com.trackmybus.theKeg.features.schedule.domain.model.CalendarDate
import com.trackmybus.theKeg.features.schedule.domain.model.ScheduleAdjustmentType
import com.trackmybus.theKeg.features.schedule.domain.repository.calendar.CalendarRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.calendarDate.CalendarDateRepository
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

class CalendarDateRepositoryTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var calendarDateRepository: CalendarDateRepository
    private lateinit var calendarRepository: CalendarRepository

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        calendarDateRepository = get()
        calendarRepository = get()
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
        runBlocking { calendarRepository.add(newCaledar) }
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDateRepository.add(newCalendarDate) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Add calendar date with existing ID`() {
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDateRepository.add(newCalendarDate) }
        val calendarDateWithExistingId =
            CalendarDate(1, "1", LocalDate(2024, 1, 1), ScheduleAdjustmentType.SERVICE_REMOVED)
        runBlocking { calendarDateRepository.add(calendarDateWithExistingId) }.run {
            assertTrue(this.isFailure)
            assertNull(this.getOrNull())
        }
    }

    @Test
    fun `Get list of all calendar dates`() {
        runBlocking {
            val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
            runBlocking { calendarDateRepository.add(newCalendarDate) }
            val calendarDates = calendarDateRepository.getAll()
            assertTrue(calendarDates.isSuccess)
            assertNotNull(calendarDates.getOrNull())
        }
    }

    @Test
    fun `Get calendar date with existing ID`() {
        val newCaledar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarRepository.add(newCaledar) }
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDateRepository.add(newCalendarDate) }
        runBlocking { calendarDateRepository.getById(1) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Get calendar date with ID that does not exist`() = runBlocking {
        val calendarDate = calendarDateRepository.getById(-1)
        assertTrue(calendarDate.isSuccess)
        assertNull(calendarDate.getOrNull())
    }

    @Test
    fun `Update existing calendar date`() = runBlocking {
        val newCaledar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarRepository.add(newCaledar) }
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDateRepository.add(newCalendarDate) }
        val updatedCalendarDate = CalendarDate(1, "1", LocalDate(2024, 1, 1), ScheduleAdjustmentType.SERVICE_REMOVED)
        val result = calendarDateRepository.update(updatedCalendarDate)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Update calendar date with non-existent ID`() = runBlocking {
        val updatedCalendarDate = CalendarDate(1, "1", LocalDate(2024, 1, 1), ScheduleAdjustmentType.SERVICE_REMOVED)
        val result = calendarDateRepository.update(updatedCalendarDate)
        assertTrue(result.isFailure)
    }

    @Test
    fun `Delete calendar date with existing ID`() {
        val newCaledar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarRepository.add(newCaledar) }
        val newCalendarDate = CalendarDate(1, "1", LocalDate(2023, 1, 1), ScheduleAdjustmentType.SERVICE_ADDED)
        runBlocking { calendarDateRepository.add(newCalendarDate) }
        runBlocking { calendarDateRepository.deleteById(1) }.run {
            assertTrue(isSuccess)
            assertTrue(getOrNull()!!)
        }
    }

    @Test
    fun `Delete calendar date with non-existing ID`() = runBlocking {
        val newCaledar = Calendar("1", true, true, true, true, true, true, true, LocalDate(2023, 1, 1), LocalDate(2023, 12, 31))
        runBlocking { calendarRepository.add(newCaledar) }
        val result = calendarDateRepository.deleteById(1)
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }
}