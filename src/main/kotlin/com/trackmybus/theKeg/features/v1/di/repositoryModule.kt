@file:Suppress("ktlint:standard:filename")

package com.trackmybus.theKeg.features.v1.di

import com.trackmybus.theKeg.di.getLogger
import com.trackmybus.theKeg.features.v1.domain.repository.agency.AgencyRepository
import com.trackmybus.theKeg.features.v1.domain.repository.agency.AgencyRepositoryImpl
import com.trackmybus.theKeg.features.v1.domain.repository.calendar.CalendarRepository
import com.trackmybus.theKeg.features.v1.domain.repository.calendar.CalendarRepositoryImpl
import com.trackmybus.theKeg.features.v1.domain.repository.calendarDate.CalendarDateRepository
import com.trackmybus.theKeg.features.v1.domain.repository.calendarDate.CalendarDateRepositoryImpl
import com.trackmybus.theKeg.features.v1.domain.repository.gtfs.GtfsScheduleRepository
import com.trackmybus.theKeg.features.v1.domain.repository.gtfs.GtfsScheduleRepositoryImpl
import com.trackmybus.theKeg.features.v1.domain.repository.gtfsQueryRepository.GtfsQueryRepository
import com.trackmybus.theKeg.features.v1.domain.repository.gtfsQueryRepository.GtfsQueryRepositoryImpl
import com.trackmybus.theKeg.features.v1.domain.repository.route.RouteRepository
import com.trackmybus.theKeg.features.v1.domain.repository.route.RouteRepositoryImpl
import com.trackmybus.theKeg.features.v1.domain.repository.shape.ShapeRepository
import com.trackmybus.theKeg.features.v1.domain.repository.shape.ShapeRepositoryImpl
import com.trackmybus.theKeg.features.v1.domain.repository.stop.StopRepository
import com.trackmybus.theKeg.features.v1.domain.repository.stop.StopRepositoryImpl
import com.trackmybus.theKeg.features.v1.domain.repository.stopTime.StopTimeRepository
import com.trackmybus.theKeg.features.v1.domain.repository.stopTime.StopTimeRepositoryImpl
import com.trackmybus.theKeg.features.v1.domain.repository.trip.TripRepository
import com.trackmybus.theKeg.features.v1.domain.repository.trip.TripRepositoryImpl
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
        single<GtfsQueryRepository> { GtfsQueryRepositoryImpl(getLogger<GtfsQueryRepositoryImpl>(), get()) }
    }
