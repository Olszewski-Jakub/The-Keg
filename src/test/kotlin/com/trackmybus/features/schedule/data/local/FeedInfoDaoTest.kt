package com.trackmybus.features.v1.data.local

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.v1.data.local.dao.feedInfo.FeedInfoDao
import com.trackmybus.theKeg.features.v1.domain.mapper.toModel
import com.trackmybus.theKeg.features.v1.domain.model.FeedInfo
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
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

class FeedInfoDaoTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var feedInfoDao: FeedInfoDao

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        databaseFactory.connect()
        feedInfoDao = get()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new feed info`() {
        val newFeedInfo =
            FeedInfo(1, "Feed Publisher", "https://feedpublisher.com", "en", LocalDate(2023, 1, 1), LocalDate(2023, 12, 31), "1.0")
        runBlocking { feedInfoDao.addFeedInfo(newFeedInfo) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
            assertEquals(newFeedInfo, this.getOrNull()?.toModel())
        }
    }

    @Test
    fun `Get list of all feed info`() =
        runBlocking {
            val newFeedInfo =
                FeedInfo(1, "Feed Publisher", "https://feedpublisher.com", "en", LocalDate(2023, 1, 1), LocalDate(2023, 12, 31), "1.0")
            runBlocking { feedInfoDao.addFeedInfo(newFeedInfo) }
            val feedInfos = feedInfoDao.getAllFeedInfo()
            assertTrue(feedInfos.isSuccess)
            assertNotNull(feedInfos.getOrNull())
        }

    @Test
    fun `Get feed info with existing ID`() {
        val newFeedInfo =
            FeedInfo(1, "Feed Publisher", "https://feedpublisher.com", "en", LocalDate(2023, 1, 1), LocalDate(2023, 12, 31), "1.0")
        runBlocking { feedInfoDao.addFeedInfo(newFeedInfo) }
        runBlocking { feedInfoDao.getFeedInfoById(1) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Get feed info with ID that does not exist`() =
        runBlocking {
            val feedInfo = feedInfoDao.getFeedInfoById(-1)
            assertTrue(feedInfo.isSuccess)
            assertNull(feedInfo.getOrNull())
        }

    @Test
    fun `Update existing feed info`() =
        runBlocking {
            val newFeedInfo =
                FeedInfo(1, "Feed Publisher", "https://feedpublisher.com", "en", LocalDate(2023, 1, 1), LocalDate(2023, 12, 31), "1.0")
            runBlocking { feedInfoDao.addFeedInfo(newFeedInfo) }
            val updatedFeedInfo =
                FeedInfo(
                    1,
                    "Updated Publisher",
                    "https://updatedpublisher.com",
                    "en",
                    LocalDate(2024, 1, 1),
                    LocalDate(2024, 12, 31),
                    "2.0",
                )
            val result = feedInfoDao.updateFeedInfo(updatedFeedInfo)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Update feed info with non-existent ID`() =
        runBlocking {
            val updatedFeedInfo =
                FeedInfo(
                    1,
                    "Updated Publisher",
                    "https://updatedpublisher.com",
                    "en",
                    LocalDate(2024, 1, 1),
                    LocalDate(2024, 12, 31),
                    "2.0",
                )
            val result = feedInfoDao.updateFeedInfo(updatedFeedInfo)
            assertTrue(result.isFailure)
        }

    @Test
    fun `Delete feed info with existing ID`() {
        val newFeedInfo =
            FeedInfo(1, "Feed Publisher", "https://feedpublisher.com", "en", LocalDate(2023, 1, 1), LocalDate(2023, 12, 31), "1.0")
        runBlocking { feedInfoDao.addFeedInfo(newFeedInfo) }
        runBlocking { feedInfoDao.deleteFeedInfo(1) }.run {
            assertTrue(isSuccess)
            assertTrue(getOrNull()!!)
        }
    }

    @Test
    fun `Delete feed info with non-existing ID`() =
        runBlocking {
            val result = feedInfoDao.deleteFeedInfo(1)
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}
