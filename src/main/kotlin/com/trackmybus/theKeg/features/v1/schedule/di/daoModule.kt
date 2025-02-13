@file:Suppress("ktlint:standard:filename")

package com.trackmybus.theKeg.features.v1.schedule.di

import com.trackmybus.theKeg.di.getLogger
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.agency.AgenciesDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.agency.AgenciesDaoImpl
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.calendar.CalendarDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.calendar.CalendarDaoImpl
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.calendarDate.CalendarDatesDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.calendarDate.CalendarDatesDaoImpl
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.feedInfo.FeedInfoDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.feedInfo.FeedInfoDaoImpl
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.route.RouteDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.route.RouteDaoImpl
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.shape.ShapeDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.shape.ShapeDaoImpl
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.stop.StopDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.stop.StopDaoImpl
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.stopTime.StopTimeDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.stopTime.StopTimeDaoImpl
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.trip.TripDao
import com.trackmybus.theKeg.features.v1.schedule.data.local.dao.trip.TripDaoImpl
import org.koin.dsl.module

val daoModule =
    module {
        single<AgenciesDao> { AgenciesDaoImpl(getLogger<AgenciesDao>(), get()) }
        single<CalendarDao> { CalendarDaoImpl(getLogger<CalendarDao>(), get()) }
        single<CalendarDatesDao> { CalendarDatesDaoImpl(getLogger<CalendarDatesDao>(), get()) }
        single<FeedInfoDao> { FeedInfoDaoImpl(getLogger<FeedInfoDao>(), get()) }
        single<ShapeDao> { ShapeDaoImpl(getLogger<ShapeDao>(), get()) }
        single<RouteDao> { RouteDaoImpl(getLogger<RouteDao>(), get()) }
        single<ShapeDao> { ShapeDaoImpl(getLogger<ShapeDao>(), get()) }
        single<StopDao> { StopDaoImpl(getLogger<StopDao>(), get()) }
        single<StopTimeDao> { StopTimeDaoImpl(getLogger<StopTimeDao>(), get()) }
        single<TripDao> { TripDaoImpl(getLogger<TripDao>(), get()) }
    }
