package com.trackmybus.theKeg.features.schedule.di

import com.trackmybus.theKeg.di.getLogger
import com.trackmybus.theKeg.features.schedule.domain.repository.agency.AgencyRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.agency.AgencyRepositoryImpl
import com.trackmybus.theKeg.features.schedule.domain.repository.calendar.CalendarRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.calendar.CalendarRepositoryImpl
import com.trackmybus.theKeg.features.schedule.domain.repository.calendarDate.CalendarDateRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.calendarDate.CalendarDateRepositoryImpl
import com.trackmybus.theKeg.features.schedule.domain.repository.gtfs.GtfsScheduleRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.gtfs.GtfsScheduleRepositoryImpl
import com.trackmybus.theKeg.features.schedule.domain.repository.route.RouteRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.route.RouteRepositoryImpl
import com.trackmybus.theKeg.features.schedule.domain.repository.shape.ShapeRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.shape.ShapeRepositoryImpl
import com.trackmybus.theKeg.features.schedule.domain.repository.stop.StopRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.stop.StopRepositoryImpl
import com.trackmybus.theKeg.features.schedule.domain.repository.stopTime.StopTimeRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.stopTime.StopTimeRepositoryImpl
import com.trackmybus.theKeg.features.schedule.domain.repository.trip.TripRepository
import com.trackmybus.theKeg.features.schedule.domain.repository.trip.TripRepositoryImpl
import org.koin.dsl.module

val repositoryModule =
    module {
        single<AgencyRepository> { AgencyRepositoryImpl(getLogger<AgencyRepositoryImpl>(), get()) }
        single<CalendarRepository> { CalendarRepositoryImpl(getLogger<CalendarRepositoryImpl>(), get()) }
        single<CalendarDateRepository> { CalendarDateRepositoryImpl(getLogger<CalendarDateRepositoryImpl>(), get()) }
        single<GtfsScheduleRepository> { GtfsScheduleRepositoryImpl(getLogger<GtfsScheduleRepositoryImpl>(), get()) }
        single<RouteRepository> { RouteRepositoryImpl(getLogger<RouteRepositoryImpl>(), get()) }
        single<ShapeRepository> { ShapeRepositoryImpl(getLogger<ShapeRepositoryImpl>(), get()) }
        single<StopRepository> { StopRepositoryImpl(getLogger<StopRepositoryImpl>(), get()) }
        single<StopTimeRepository> { StopTimeRepositoryImpl(getLogger<StopTimeRepositoryImpl>(), get()) }
        single<TripRepository> { TripRepositoryImpl(getLogger<TripRepositoryImpl>(), get()) }
    }
