package com.trackmybus.features.v1.schedule.data.local

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.agency.AgenciesDao
import com.trackmybus.theKeg.features.v1.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Agency
import kotlinx.coroutines.runBlocking
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

class AgenciesDaoTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var agenciesDao: AgenciesDao

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        databaseFactory.connect()
        agenciesDao = get()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new agency`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
            assertEquals(newAgency, this.getOrNull()?.toModel())
        }
    }

    @Test
    fun `Add agency with existing ID`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }
        val agencyWithExistingId = Agency("1", "Agency with existing ID", "https://agencywithexistingid.com", "UTC")
        runBlocking { agenciesDao.addAgency(agencyWithExistingId) }.run {
            assertTrue(this.isFailure)
            assertNull(this.getOrNull())
        }
    }

    @Test
    fun `Get list of all agencies`() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            runBlocking { agenciesDao.addAgency(newAgency) }
            val agencies = agenciesDao.getAllAgencies()
            assertTrue(agencies.isSuccess)
            assertNotNull(agencies.getOrNull())
        }

    @Test
    fun `Get agency with existing ID`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }
        runBlocking { agenciesDao.getAgencyById("1") }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Get agency with ID that does not exist`() =
        runBlocking {
            val agency = agenciesDao.getAgencyById("1")
            assertTrue(agency.isSuccess)
            assertNull(agency.getOrNull())
        }

    @Test
    fun `Update existing agency`() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            runBlocking { agenciesDao.addAgency(newAgency) }
            val updatedAgency = Agency("1", "Updated Agency", "https://updatedagency.com", "UTC")
            val result = agenciesDao.updateAgency(updatedAgency)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Update agency with non-existent ID`() =
        runBlocking {
            val updatedAgency = Agency("1", "Updated Agency", "https://updatedagency.com", "UTC")
            val result = agenciesDao.updateAgency(updatedAgency)
            assertTrue(result.isFailure)
        }

    @Test
    fun `Delete agency with existing ID`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }
        runBlocking { agenciesDao.deleteAgency("1") }.run {
            assertTrue(isSuccess)
            assertTrue(getOrNull()!!)
        }
    }

    @Test
    fun `Delete agency with non-existing ID`() =
        runBlocking {
            val result = agenciesDao.deleteAgency("1")
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}
