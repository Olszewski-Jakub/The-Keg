package com.trackmybus.features.v1.domain.repository

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.features.v1.domain.repository.gtfs.GtfsScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.assertNotNull

class GtfsScheduleRepositoryTest : KoinTest {
    private lateinit var gtfsScheduleService: GtfsScheduleRepository

    @Before
    fun setUp() {
        configureKoinUnitTest()
        gtfsScheduleService = get()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Fetch GTFS and check if fields are not empty`() =
        runBlocking(Dispatchers.IO) {
            val result = gtfsScheduleService.fetchGtfsData()
            val gtfsDto = result.getOrNull()
            assertNotNull(gtfsDto)
            assertFalse(gtfsDto.agencies.isEmpty())
            assertFalse(gtfsDto.calendars.isEmpty())
            assertFalse(gtfsDto.calendarDates.isEmpty())
            assertFalse(gtfsDto.routes.isEmpty())
            assertFalse(gtfsDto.shapes.isEmpty())
            assertFalse(gtfsDto.shapes.isEmpty())
            assertFalse(gtfsDto.stopTimes.isEmpty())
            assertFalse(gtfsDto.trips.isEmpty())
            assertFalse(gtfsDto.feedInfo.isEmpty())
        }
}
