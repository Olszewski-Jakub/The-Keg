package com.trackmybus.features.schedule.domain.repository

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.domain.model.Shape
import com.trackmybus.theKeg.features.schedule.domain.repository.shape.ShapeRepository
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

class ShapeRepositoryTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var shapeRepository: ShapeRepository

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        databaseFactory.connect()
        shapeRepository = get()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new shape`() =
        runBlocking {
            val newShape = Shape(1, "shape_id", 1.0, 1.0, 1, 0.0)
            val result = shapeRepository.add(newShape)
            assertTrue(result.isSuccess)
            assertNotNull(result.getOrNull())
            assertEquals(newShape, result.getOrNull())
        }

    @Test
    fun `Get all shapes`() =
        runBlocking {
            val newShape1 = Shape(1, "shape_id_1", 1.0, 1.0, 1, 0.0)
            val newShape2 = Shape(2, "shape_id_2", 2.0, 2.0, 2, 0.0)
            shapeRepository.add(newShape1)
            shapeRepository.add(newShape2)
            val result = shapeRepository.getAll()
            assertTrue(result.isSuccess)
            assertNotNull(result.getOrNull())
            assertEquals(2, result.getOrNull()!!.size)
            assertTrue(result.getOrNull()!!.contains(newShape1))
            assertTrue(result.getOrNull()!!.contains(newShape2))
        }

    @Test
    fun `Get shape by existing ID`() =
        runBlocking {
            val newShape = Shape(1, "shape_id", 1.0, 1.0, 1, 0.0)
            shapeRepository.add(newShape)
            val result = shapeRepository.getById(1)
            assertTrue(result.isSuccess)
            assertNotNull(result.getOrNull())
            assertEquals(newShape, result.getOrNull())
        }

    @Test
    fun `Get shape by non-existent ID`() =
        runBlocking {
            val result = shapeRepository.getById(999)
            assertTrue(result.isSuccess)
            assertNull(result.getOrNull())
        }

    @Test
    fun `Update existing shape`() =
        runBlocking {
            val newShape = Shape(1, "shape_id", 1.0, 1.0, 1, 0.0)
            shapeRepository.add(newShape)
            val updatedShape = Shape(1, "shape_id", 12.0, 12.0, 12, 1.0)
            val result = shapeRepository.update(updatedShape)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Update non-existent shape`() =
        runBlocking {
            val updatedShape = Shape(1, "shape_id", 12.0, 12.0, 12, 1.0)
            val result = shapeRepository.update(updatedShape)
            assertTrue(result.isFailure)
        }

    @Test
    fun `Delete shape by existing ID`() =
        runBlocking {
            val newShape = Shape(1, "shape_id", 1.0, 1.0, 1, 0.0)
            shapeRepository.add(newShape)
            val result = shapeRepository.deleteById(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Delete shape by non-existent ID`() =
        runBlocking {
            val result = shapeRepository.deleteById(999)
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }
}
