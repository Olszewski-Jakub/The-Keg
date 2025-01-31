package com.trackmybus.theKeg.features.schedule.domain.mapper

import com.trackmybus.theKeg.features.schedule.data.remote.dto.*
import com.trackmybus.theKeg.features.schedule.domain.model.*
import com.trackmybus.theKeg.infrastructure.dates.DateUtil.toDateObject
import com.trackmybus.theKeg.infrastructure.dates.DateUtil.toFormattedString
import com.trackmybus.theKeg.infrastructure.dates.DateUtil.toIntDate
import com.trackmybus.theKeg.infrastructure.dates.DateUtil.toLocalTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

/** Agency */
fun AgencyDto.toModel() = Agency(
    agencyId = this.agencyId ?: "",
    agencyName = this.agencyName ?: "",
    agencyUrl = this.agencyUrl ?: "",
    agencyTimezone = this.agencyTimezone ?: ""
)

fun Agency.toDto() = AgencyDto(
    agencyId = this.agencyId,
    agencyName = this.agencyName,
    agencyUrl = this.agencyUrl,
    agencyTimezone = this.agencyTimezone
)

fun List<AgencyDto>.toAgencyModel() = this.map { it.toModel() }

fun List<Agency>.toAgencyDto() = this.map { it.toDto() }

/** CalendarDate */
fun CalendarDateDto.toModel() = CalendarDate(
    id = 0,
    serviceId = this.serviceId ?: "",
    date = this.date?.toDateObject() ?: LocalDate(2025, 1, 1),
    exceptionType = ScheduleAdjustmentType.fromInt(this.exceptionType ?: 0)
)

fun CalendarDate.toDto() = CalendarDateDto(
    serviceId = this.serviceId,
    date = this.date.toIntDate(),
    exceptionType = ScheduleAdjustmentType.toInt(this.exceptionType)
)

fun List<CalendarDateDto>.toCalendarDateModel() = this.map { it.toModel() }

fun List<CalendarDate>.toCalendarDateDto() = this.map { it.toDto() }

/** Calendar */
fun CalendarDto.toModel() = Calendar(
    serviceId = this.serviceId ?: "0",
    monday = this.monday == true,
    tuesday = this.tuesday == true,
    wednesday = this.wednesday == true,
    thursday = this.thursday == true,
    friday = this.friday == true,
    saturday = this.saturday == true,
    sunday = this.sunday == true,
    startDate = this.startDate?.toDateObject() ?: LocalDate(2025, 1, 1),
    endDate = this.endDate?.toDateObject() ?: LocalDate(2025, 1, 1)
)

fun Calendar.toDto() = CalendarDto(
    serviceId = this.serviceId,
    monday = this.monday,
    tuesday = this.tuesday,
    wednesday = this.wednesday,
    thursday = this.thursday,
    friday = this.friday,
    saturday = this.saturday,
    sunday = this.sunday,
    startDate = this.startDate.toIntDate(),
    endDate = this.endDate.toIntDate()
)

fun List<CalendarDto>.toCalendarModel() = this.map { it.toModel() }

fun List<Calendar>.toCalendarDto() = this.map { it.toDto() }

/** Feed Info */
fun FeedInfoDto.toModel() = FeedInfo(
    feedPublisherName = this.feedPublisherName ?: "",
    feedPublisherUrl = this.feedPublisherUrl ?: "",
    feedLang = this.feedLang ?: "",
    feedStartDate = this.feedStartDate?.toDateObject() ?: LocalDate(2025, 1, 1),
    feedEndDate = this.feedEndDate?.toDateObject() ?: LocalDate(2025, 1, 1),
    feedVersion = this.feedVersion ?: "",
    id = 0
)

fun FeedInfo.toDto() = FeedInfoDto(
    feedPublisherName = this.feedPublisherName,
    feedPublisherUrl = this.feedPublisherUrl,
    feedLang = this.feedLang,
    feedStartDate = this.feedStartDate.toIntDate(),
    feedEndDate = this.feedEndDate.toIntDate(),
    feedVersion = this.feedVersion
)

fun List<FeedInfoDto>.toFeedInfoModel() = this.map { it.toModel() }

fun List<FeedInfo>.toFeedInfoDto() = this.map { it.toDto() }

/** Route */
fun RouteDto.toModel() = Route(
    routeId = this.routeId ?: "",
    agencyId = this.agencyId ?: "",
    routeShortName = this.routeShortName ?: "",
    routeLongName = this.routeLongName ?: "",
    routeType = this.routeType ?: 0,
    routeDescription = this.routeDesc ?: "",
    routeUrl = this.routeUrl ?: "",
    routeColor = this.routeColor ?: "",
    routeTextColor = this.routeTextColor ?: "",
)

fun Route.toDto() = RouteDto(
    routeId = this.routeId,
    agencyId = this.agencyId,
    routeShortName = this.routeShortName,
    routeLongName = this.routeLongName,
    routeType = this.routeType
)

fun List<RouteDto>.toRouteModel() = this.map { it.toModel() }

fun List<Route>.toRouteDto() = this.map { it.toDto() }

/** Shape */
fun ShapeDto.toModel() = Shape(
    shapeId = this.shapeId ?: "",
    shapePtLat = this.shapePtLat ?: 0.0,
    shapePtLon = this.shapePtLon ?: 0.0,
    shapePtSequence = this.shapePtSequence ?: 0,
    shapeDistTraveled = this.shapeDistTraveled ?: 0.0,
    id = 0
)

fun Shape.toDto() = ShapeDto(
    shapeId = this.shapeId,
    shapePtLat = this.shapePtLat,
    shapePtLon = this.shapePtLon,
    shapePtSequence = this.shapePtSequence,
    shapeDistTraveled = this.shapeDistTraveled
)

fun List<ShapeDto>.toShapeModel() = this.map { it.toModel() }

fun List<Shape>.toShapeDto() = this.map { it.toDto() }

/** Stop */
fun StopDto.toModel() = Stop(
    stopId = this.stopId ?: "",
    stopCode = this.stopCode ?: 0,
    stopName = this.stopName ?: "",
    stopLat = this.stopLat ?: 0.0,
    stopLon = this.stopLon ?: 0.0,
    stopDesc = this.stopDesc ?: "",
    zoneId = this.zoneId ?: "",
    stopUrl = this.stopUrl ?: "",
    locationType = this.locationType,
    parentStation = this.parentStation ?: "",
)

fun Stop.toDto() = StopDto(
    stopId = this.stopId,
    stopCode = this.stopCode,
    stopName = this.stopName,
    stopLat = this.stopLat,
    stopLon = this.stopLon
)

fun List<StopDto>.toStopModel() = this.map { it.toModel() }

fun List<Stop>.toStopDto() = this.map { it.toDto() }

/** Stop Time */
fun StopTimeDto.toModel() = StopTime(
    tripId = this.tripId ?: "",
    arrivalTime = this.arrivalTime?.takeIf { isValidTime(it) }?.toLocalTime() ?: LocalTime(0, 0),
    departureTime = this.departureTime?.takeIf { isValidTime(it) }?.toLocalTime() ?: LocalTime(0, 0),
    stopId = this.stopId ?: "",
    stopSequence = this.stopSequence ?: 0,
    stopHeadsign = this.stopHeadsign ?: "",
    pickupType = this.pickupType ?: 0,
    dropOffType = this.dropOffType ?: 0,
    timepoint = this.timepoint ?: 0,
    id = 0
)

fun isValidTime(time: String): Boolean {
    val parts = time.split(":")
    if (parts.size != 3) return false
    val (hour, minute, second) = parts.map { it.toIntOrNull() ?: return false }
    return hour in 0..24 && minute in 0..59 && second in 0..59
}
fun StopTime.toDto() = StopTimeDto(
    tripId = this.tripId,
    arrivalTime = this.arrivalTime.toFormattedString(),
    departureTime = this.departureTime.toFormattedString(),
    stopId = this.stopId,
    stopSequence = this.stopSequence,
    stopHeadsign = this.stopHeadsign,
    pickupType = this.pickupType,
    dropOffType = this.dropOffType,
    timepoint = this.timepoint
)

fun List<StopTimeDto>.toStopTimeModel() = this.map { it.toModel() }

fun List<StopTime>.toStopTimeDto() = this.map { it.toDto() }

/** Trip */
fun TripDto.toModel() = Trip(
    routeId = this.routeId ?: "",
    serviceId = this.serviceId ?: "",
    tripId = this.tripId ?: "",
    tripHeadsign = this.tripHeadsign ?: "",
    tripShortName = this.tripShortName ?: "",
    directionId = this.directionId ?: 0,
    blockId = this.blockId ?: "",
    shapeId = this.shapeId ?: ""
)

fun Trip.toDto() = TripDto(
    routeId = this.routeId,
    serviceId = this.serviceId,
    tripId = this.tripId,
    tripHeadsign = this.tripHeadsign,
    tripShortName = this.tripShortName,
    directionId = this.directionId,
    blockId = this.blockId,
    shapeId = this.shapeId
)

fun List<TripDto>.toTripModel() = this.map { it.toModel() }

fun List<Trip>.toTripDto() = this.map { it.toDto() }

/** GTFS */
fun GTFSDto.toModel() = GTFS(
    agencies = this.agencyDto.toAgencyModel(),
    calendarDates = this.calendarDatesDto.toCalendarDateModel(),
    calendars = this.calendarDto.toCalendarModel(),
    feedInfo = this.feedInfoDto.toFeedInfoModel(),
    routes = this.routesDto.toRouteModel(),
    shapes = this.shapesDto.toShapeModel(),
    stops = this.stopsDto.toStopModel(),
    stopTimes = this.stopTimesDto.toStopTimeModel(),
    trips = this.tripsDto.toTripModel()
)

fun GTFS.toDto() = GTFSDto(
    agencyDto = this.agencies.toAgencyDto(),
    calendarDatesDto = this.calendarDates.toCalendarDateDto(),
    calendarDto = this.calendars.toCalendarDto(),
    feedInfoDto = this.feedInfo.toFeedInfoDto(),
    routesDto = this.routes.toRouteDto(),
    shapesDto = this.shapes.toShapeDto(),
    stopsDto = this.stops.toStopDto(),
    stopTimesDto = this.stopTimes.toStopTimeDto(),
    tripsDto = this.trips.toTripDto()
)