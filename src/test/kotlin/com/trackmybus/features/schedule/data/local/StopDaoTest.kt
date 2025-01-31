package com.trackmybus.features.schedule.data.local

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.dao.stop.StopDao
import com.trackmybus.theKeg.features.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.schedule.domain.model.Stop
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.Test

class StopDaoTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var stopDao: StopDao

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        databaseFactory.connect()
        stopDao = get()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new stop`() {
        val newStop = Stop("1", 1, "Stop 1 Description", 1.0, 1.0, "", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { stopDao.addStop(newStop) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
            assertEquals(newStop, this.getOrNull()?.toModel())
        }
    }

    @Test
    fun `Add stop with existing ID`() {
        val newStop =
            Stop("1", 1, "Stop 1 Description", 1.0, 1.0, "", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { stopDao.addStop(newStop) }
        val stopWithExistingId = Stop(
            "1",
            1,
            "Stop with existing ID Description",
            2.0,
            2.0, "",
            "Zone 2",
            "Stop URL 2",
            2,
            "Parent Station 2"
        )
        runBlocking { stopDao.addStop(stopWithExistingId) }.run {
            assertTrue(this.isFailure)
            assertNull(this.getOrNull())
        }
    }

    @Test
    fun `Get list of all stops`() = runBlocking {
        val newStop =
            Stop("1", 1, "Stop 1 Description", 1.0, 1.0, "", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { stopDao.addStop(newStop) }
        val stops = stopDao.getAllStops()
        assertTrue(stops.isSuccess)
        assertNotNull(stops.getOrNull())
    }

    @Test
    fun `Get stop with existing ID`() {
        val newStop =
            Stop("1", 1, "Stop 1 Description", 1.0, 1.0, "", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { stopDao.addStop(newStop) }
        runBlocking { stopDao.getStopById("1") }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Get stop with ID that does not exist`() = runBlocking {
        val stop = stopDao.getStopById("non-existent-id")
        assertTrue(stop.isSuccess)
        assertNull(stop.getOrNull())
    }

    @Test
    fun `Update existing stop`() = runBlocking {
        val newStop =
            Stop("1", 1, "Stop 1 Description", 1.0, 1.0, "", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { stopDao.addStop(newStop) }
        val updatedStop = Stop(
            "1",
            2,
            "Updated Stop Description",
            2.0,
            2.0, "",
            "Zone 2",
            "Updated Stop URL",
            2,
            "Updated Parent Station"
        )
        val result = stopDao.updateStop(updatedStop)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Update stop with non-existent ID`() = runBlocking {
        val updatedStop = Stop(
            "non-existent-id",
            2,
            "Updated Stop Description",
            2.0,
            2.0, "",
            "Zone 2",
            "Updated Stop URL",
            1,
            "Updated Parent Station"
        )
        val result = stopDao.updateStop(updatedStop)
        assertTrue(result.isFailure)
    }

    @Test
    fun `Delete stop with existing ID`() {
        val newStop =
            Stop("1", 2, "Stop 1 Description", 1.0, 1.0, "", "Zone 1", "Stop URL", 1, "Parent Station")
        runBlocking { stopDao.addStop(newStop) }
        runBlocking { stopDao.deleteStop("1") }.run {
            assertTrue(isSuccess)
            assertTrue(getOrNull()!!)
        }
    }

    @Test
    fun `Delete stop with non-existing ID`() = runBlocking {
        val result = stopDao.deleteStop("non-existent-id")
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }
}