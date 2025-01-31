package com.trackmybus.features.schedule.data.remote

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.features.schedule.data.remote.service.GtfsScheduleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.assertNotNull

class GtfsScheduleServiceTest : KoinTest {
    private lateinit var gtfsScheduleService: GtfsScheduleService

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
    fun `Fetch GTFS and check if fields are not empty`() = runBlocking(Dispatchers.IO) {
        val result = gtfsScheduleService.fetchGtfsData()
        assertTrue(result.isSuccess)
        val gtfsDto = result.getOrNull()
        assertNotNull(gtfsDto)
        assertFalse(gtfsDto.agencyDto.isEmpty())
        assertFalse(gtfsDto.calendarDto.isEmpty())
        assertFalse(gtfsDto.calendarDatesDto.isEmpty())
        assertFalse(gtfsDto.routesDto.isEmpty())
        assertFalse(gtfsDto.shapesDto.isEmpty())
        assertFalse(gtfsDto.stopsDto.isEmpty())
        assertFalse(gtfsDto.stopTimesDto.isEmpty())
        assertFalse(gtfsDto.tripsDto.isEmpty())
        assertFalse(gtfsDto.feedInfoDto.isEmpty())
    }
}