package com.trackmybus.theKeg.features.v1.schedule.domain.usecases

import com.trackmybus.theKeg.features.v1.schedule.data.remote.dto.DepartureTimeDto
import com.trackmybus.theKeg.features.v1.schedule.data.remote.dto.RouteDto
import com.trackmybus.theKeg.features.v1.schedule.data.remote.dto.RouteStopInfoDto
import com.trackmybus.theKeg.features.v1.schedule.data.remote.dto.ShapeDto
import com.trackmybus.theKeg.features.v1.schedule.data.remote.dto.StopInSequenceDto
import com.trackmybus.theKeg.features.v1.schedule.data.remote.dto.StopOnTripDto
import com.trackmybus.theKeg.features.v1.schedule.domain.mapper.toDto
import com.trackmybus.theKeg.features.v1.schedule.domain.mapper.toRouteDto
import com.trackmybus.theKeg.features.v1.schedule.domain.mapper.toRouteStopInfoDto
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Agency
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Calendar
import com.trackmybus.theKeg.features.v1.schedule.domain.model.CalendarDate
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Shape
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Stop
import com.trackmybus.theKeg.features.v1.schedule.domain.model.StopTime
import com.trackmybus.theKeg.features.v1.schedule.domain.model.Trip
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.agency.AgencyRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.calendar.CalendarRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.calendarDate.CalendarDateRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.gtfs.GtfsScheduleRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.gtfsQueryRepository.GtfsQueryRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.route.RouteRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.shape.ShapeRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.stop.StopRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.stopTime.StopTimeRepository
import com.trackmybus.theKeg.features.v1.schedule.domain.repository.trip.TripRepository
import com.trackmybus.theKeg.features.v1.schedule.resource.RouteType
import com.trackmybus.theKeg.infrastructure.mappers.ResultMapper.mapResult
import io.ktor.util.logging.Logger
import kotlinx.datetime.LocalDate

class ScheduleUseCaseImp(
    private val logger: Logger,
    private val gtfsScheduleRepository: GtfsScheduleRepository,
    private val agencyRepository: AgencyRepository,
    private val calendarRepository: CalendarRepository,
    private val calendarDatesRepository: CalendarDateRepository,
    private val routeRepository: RouteRepository,
    private val shapeRepository: ShapeRepository,
    private val stopRepository: StopRepository,
    private val stopTimeRepository: StopTimeRepository,
    private val tripRepository: TripRepository,
    private val gtfsQueryRepository: GtfsQueryRepository,
) : ScheduleUseCase {
    override suspend fun fetchAndSaveGtfsData(): Result<Unit> {
        val gtfsData = gtfsScheduleRepository.fetchGtfsData().getOrNull()
        saveAgency(gtfsData?.agencies ?: emptyList())
        saveCalendar(gtfsData?.calendars ?: emptyList())
        saveCalendarDate(gtfsData?.calendarDates ?: emptyList())
        saveCalendarDate(gtfsData?.calendarDates ?: emptyList())
        saveRoute(gtfsData?.routes ?: emptyList())
        saveShape(gtfsData?.shapes ?: emptyList())
        saveStop(gtfsData?.stops ?: emptyList())
        saveTrip(gtfsData?.trips ?: emptyList())
        saveStopTime(gtfsData?.stopTimes ?: emptyList())
        return Result.success(Unit)
    }

    override suspend fun getRouteDirections(routeId: String): Result<List<RouteStopInfoDto>> =
        gtfsQueryRepository.getFirstAndLastStopsForRoute(routeId).mapResult { it.toRouteStopInfoDto() }

    override suspend fun getStopsForGivenRoute(
        routeId: String,
        firstStopId: String,
        endStopId: String,
    ): Result<List<StopInSequenceDto>> = gtfsQueryRepository.getStopsForGivenRoute(routeId, firstStopId, endStopId)

    override suspend fun getAllRoutes(): Result<List<RouteDto>> = routeRepository.getAll().mapResult { it.toRouteDto() }

    override suspend fun getRouteByType(type: RouteType): Result<List<RouteDto>> =
        routeRepository.getRoutesByType(type).mapResult { it.toRouteDto() }

    override suspend fun getAllDepartureTimesForStop(
        stopId: String,
        routeId: String,
        dateTime: LocalDate,
    ): Result<List<DepartureTimeDto>> =
        gtfsQueryRepository.getAllDepartureTimesForStop(
            stopId = stopId,
            routeId = routeId,
            dateTime = dateTime,
        )

    override suspend fun getStopsForTrip(tripId: String): Result<List<StopOnTripDto>> = gtfsQueryRepository.getStopsForTrip(tripId = tripId)

    override suspend fun getRoutesPassingThroughStop(stopId: String): Result<List<RouteDto>> =
        gtfsQueryRepository.getRoutesPassingThroughStop(stopId = stopId)

    override suspend fun getShapesForTrip(tripId: String): Result<List<ShapeDto>> =
        runCatching {
            tripRepository.getById(tripId).getOrNull()?.let { trip ->
                shapeRepository.getByShapeId(trip.shapeId).getOrNull()?.map { it.toDto() }
            } ?: emptyList()
        }.onFailure {
            logger.error("Error getting shapes for trip: $tripId", it)
        }

    private suspend fun saveAgency(agencies: List<Agency>) {
        val currentAgencies = agencyRepository.getAll().getOrNull()
        for (agency in agencies) {
            if (currentAgencies?.find { it.agencyId == agency.agencyId } == null) {
                agencyRepository.add(agency)
            } else {
                agencyRepository.update(agency)
            }
        }
    }

    private suspend fun saveCalendar(calendar: List<Calendar>) {
        val currentCalendar = calendarRepository.getAll().getOrNull()
        for (calendar in calendar) {
            if (currentCalendar?.find { it.serviceId == calendar.serviceId } == null) {
                calendarRepository.add(calendar)
            } else {
                calendarRepository.update(calendar)
            }
        }
    }

    private suspend fun saveCalendarDate(calendarDates: List<CalendarDate>) {
        val currentCalendarDates = calendarDatesRepository.getAll().getOrNull()
        for (calendarDate in calendarDates) {
            if (currentCalendarDates?.find { it.serviceId == calendarDate.serviceId } == null) {
                calendarDatesRepository.add(calendarDate)
            } else {
                calendarDatesRepository.update(calendarDate)
            }
        }
    }

    private suspend fun saveRoute(routes: List<com.trackmybus.theKeg.features.v1.schedule.domain.model.Route>) {
        val currentRoutes = routeRepository.getAll().getOrNull()
        for (route in routes) {
            if (currentRoutes?.find { it.routeId == route.routeId } == null) {
                routeRepository.add(route)
            } else {
                routeRepository.update(route)
            }
        }
    }

    private suspend fun saveShape(shapes: List<Shape>) {
        val currentShapes = shapeRepository.getAll().getOrNull()
        for (shape in shapes) {
            if (currentShapes?.find { it.shapeId == shape.shapeId } == null) {
                shapeRepository.add(shape)
            } else {
                shapeRepository.update(shape)
            }
        }
    }

    private suspend fun saveStop(stops: List<Stop>) {
        val currentStops = stopRepository.getAll().getOrNull()
        for (stop in stops) {
            if (currentStops?.find { it.stopId == stop.stopId } == null) {
                stopRepository.add(stop)
            } else {
                stopRepository.update(stop)
            }
        }
    }

    private suspend fun saveStopTime(stopTimes: List<StopTime>) {
        val currentStopTimes = stopTimeRepository.getAll().getOrNull()
        for (stopTime in stopTimes) {
            if (currentStopTimes?.find { it.tripId == stopTime.tripId } == null) {
                stopTimeRepository.add(stopTime)
            } else {
                stopTimeRepository.update(stopTime)
            }
        }
    }

    private suspend fun saveTrip(trips: List<Trip>) {
        val currentTrips = tripRepository.getAll().getOrNull()
        for (trip in trips) {
            if (currentTrips?.find { it.tripId == trip.tripId } == null) {
                tripRepository.add(trip)
            } else {
                tripRepository.update(trip)
            }
        }
    }
}
