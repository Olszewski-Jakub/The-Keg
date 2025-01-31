package com.trackmybus.features.schedule.data.local

import com.trackmybus.di.configureKoinUnitTest
import com.trackmybus.theKeg.database.DatabaseFactory
import com.trackmybus.theKeg.features.schedule.data.local.dao.agency.AgenciesDao
import com.trackmybus.theKeg.features.schedule.data.local.dao.route.RouteDao
import com.trackmybus.theKeg.features.schedule.domain.mapper.toModel
import com.trackmybus.theKeg.features.schedule.domain.model.Agency
import com.trackmybus.theKeg.features.schedule.domain.model.Route
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.Test

class RouteDaoTest : KoinTest {
    private lateinit var databaseFactory: DatabaseFactory
    private lateinit var routeDao: RouteDao
    private lateinit var agenciesDao: AgenciesDao

    @Before
    fun setUp() {
        configureKoinUnitTest()
        databaseFactory = get<DatabaseFactory>()
        databaseFactory.connect()
        routeDao = get()
        agenciesDao = get()
    }

    @After
    fun tearDown() {
        databaseFactory.close()
        stopKoin()
    }

    @Test
    fun `Add new route`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }
        val newRoute = Route("1","1", "Route 1", "Route 1 Long", 1, "Description 1", "https://route1.com", "FFFFFF", "000000")
        runBlocking { routeDao.addRoute(newRoute) }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
            assertEquals(newRoute, this.getOrNull()?.toModel())
        }
    }

    @Test
    fun `Add route with existing ID`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }
        val newRoute = Route("1","1", "Route 1", "Route 1 Long", 1, "Description 1", "https://route1.com", "FFFFFF", "000000")
        runBlocking { routeDao.addRoute(newRoute) }
        val routeWithExistingId = Route("1","1", "Route with existing ID", "Route with existing ID Long", 1, "Description 2", "https://route2.com", "FFFFFF", "000000")
        runBlocking { routeDao.addRoute(routeWithExistingId) }.run {
            assertTrue(this.isFailure)
            assertNull(this.getOrNull())
        }
    }

    @Test
    fun `Get list of all routes`() = runBlocking {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }
        val newRoute = Route("1","1", "Route 1", "Route 1 Long", 1, "Description 1", "https://route1.com", "FFFFFF", "000000")
        runBlocking { routeDao.addRoute(newRoute) }
        val routes = routeDao.getAllRoutes()
        assertTrue(routes.isSuccess)
        assertNotNull(routes.getOrNull())
    }

    @Test
    fun `Get route with existing ID`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }
        val newRoute = Route("1","1", "Route 1", "Route 1 Long", 1, "Description 1", "https://route1.com", "FFFFFF", "000000")
        runBlocking { routeDao.addRoute(newRoute) }
        runBlocking { routeDao.getRouteById("1") }.run {
            assertTrue(this.isSuccess)
            assertNotNull(this.getOrNull())
        }
    }

    @Test
    fun `Get route with ID that does not exist`() = runBlocking {
        val route = routeDao.getRouteById("non-existent-id")
        assertTrue(route.isSuccess)
        assertNull(route.getOrNull())
    }

    @Test
    fun `Update existing route`() = runBlocking {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }
        val newRoute = Route("1","1", "Route 1", "Route 1 Long", 1, "Description 1", "https://route1.com", "FFFFFF", "000000")
        runBlocking { routeDao.addRoute(newRoute) }
        val updatedRoute = Route("1","1", "Updated Route", "Updated Route Long", 1, "Updated Description", "https://updatedroute.com", "FFFFFF", "000000")
        val result = routeDao.updateRoute(updatedRoute)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Update route with non-existent ID`() = runBlocking {
        val updatedRoute = Route("non-existent-id", "1", "Updated Route", "Updated Route Long", 1, "Updated Description", "https://updatedroute.com", "FFFFFF", "000000")
        val result = routeDao.updateRoute(updatedRoute)
        assertTrue(result.isFailure)
    }

    @Test
    fun `Delete route with existing ID`() {
        val newAgency = Agency("1", "New Agency", "https://newagency.com", "UTC")
        runBlocking { agenciesDao.addAgency(newAgency) }
        val newRoute = Route("1","1", "Route 1", "Route 1 Long", 1, "Description 1", "https://route1.com", "FFFFFF", "000000")
        runBlocking { routeDao.addRoute(newRoute) }
        runBlocking { routeDao.deleteRoute("1") }.run {
            assertTrue(isSuccess)
            assertTrue(getOrNull()!!)
        }
    }

    @Test
    fun `Delete route with non-existing ID`() = runBlocking {
        val result = routeDao.deleteRoute("non-existent-id")
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }
}