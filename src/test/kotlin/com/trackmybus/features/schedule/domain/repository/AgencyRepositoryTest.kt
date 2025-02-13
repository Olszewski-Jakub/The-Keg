package com.trackmybus.features.v1.domain.repository

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.domain.model.Agency
import com.trackmybus.theKeg.features.v1.domain.repository.agency.AgencyRepository
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

class AgencyRepositoryTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var agencyRepository: AgencyRepository

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        databaseFactory.connect()
        agencyRepository = get()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new agency`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agencyRepository.add(newAgency) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
            assertEquals(newAgency, this.getOrNull())
        }
    }

    @Test
    fun `Add agency with existing ID`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agencyRepository.add(newAgency) }
        val agencyWithExistingId = Agency("1", "Agency with existing ID", "https://agencywithexistingid.com", "UTC")
        runBlocking { agencyRepository.add(agencyWithExistingId) }.run {
            assertTrue(this.isFailure)
            assertNull(this.getOrNull())
        }
    }

    @Test
    fun `Get list of all agencies`() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            runBlocking { agencyRepository.add(newAgency) }
            val agencies = agencyRepository.getAll()
            assertTrue(agencies.isSuccess)
            assertNotNull(agencies.getOrNull())
        }

    @Test
    fun `Get agency with existing ID`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agencyRepository.add(newAgency) }
        runBlocking { agencyRepository.getById("1") }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Get agency with ID that does not exist`() =
        runBlocking {
            val agency = agencyRepository.getById("-1")
            assertTrue(agency.isSuccess)
            assertNull(agency.getOrNull())
        }

    @Test
    fun `Update existing agency`() =
        runBlocking {
            val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
            runBlocking { agencyRepository.add(newAgency) }
            val updatedAgency = Agency("1", "Updated Agency", "https://updatedagency.com", "UTC")
            val result = agencyRepository.update(updatedAgency)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Update agency with non-existent ID`() =
        runBlocking {
            val updatedAgency = Agency("1", "Updated Agency", "https://updatedagency.com", "UTC")
            val result = agencyRepository.update(updatedAgency)
            assertTrue(result.isFailure)
        }

    @Test
    fun `Delete agency with existing ID`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agencyRepository.add(newAgency) }
        runBlocking { agencyRepository.deleteById("1") }.run {
            assertTrue(isSuccess)
            assertTrue(getOrNull()!!)
        }
    }

    @Test
    fun `Delete agency with non-existing ID`() =
        runBlocking {
            val result = agencyRepository.deleteById("1")
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}
