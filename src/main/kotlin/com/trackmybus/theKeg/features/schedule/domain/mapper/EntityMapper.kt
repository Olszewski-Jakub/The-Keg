package com.trackmybus.theKeg.features.schedule.domain.mapper

import com.trackmybus.theKeg.features.schedule.data.local.entity.*
import com.trackmybus.theKeg.features.schedule.data.local.tables.*
import com.trackmybus.theKeg.features.schedule.domain.model.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.exposed.dao.id.EntityID

fun <T, R> List<T>.mapTo(mapper: (T) -> R): List<R> = map(mapper)

fun AgencyEntity.toModel() =
    Agency(
        agencyId = this.agencyId.value,
        agencyName = this.agencyName ?: "",
        agencyUrl = this.agencyUrl ?: "",
        agencyTimezone = this.agencyTimezone ?: "",
    )

fun Agency.toEntity() =
    AgencyEntity.new {
        agencyId = EntityID(this@toEntity.agencyId, AgenciesTable)
        agencyName = this@toEntity.agencyName
        agencyUrl = this@toEntity.agencyUrl
        agencyTimezone = this@toEntity.agencyTimezone
    }

fun CalendarEntity.toModel() =
    Calendar(
        serviceId = this.serviceId.value,
        monday = this.monday == true,
        tuesday = this.tuesday == true,
        wednesday = this.wednesday == true,
        thursday = this.thursday == true,
        friday = this.friday == true,
        saturday = this.saturday == true,
        sunday = this.sunday == true,
        startDate = this.startDate ?: LocalDate(0, 1, 1),
        endDate = this.endDate ?: LocalDate(0, 1, 1),
    )

fun Calendar.toEntity() =
    CalendarEntity.new {
        serviceId = EntityID(this@toEntity.serviceId, CalendarsTable)
        monday = this@toEntity.monday
        tuesday = this@toEntity.tuesday
        wednesday = this@toEntity.wednesday
        thursday = this@toEntity.thursday
        friday = this@toEntity.friday
        saturday = this@toEntity.saturday
        sunday = this@toEntity.sunday
        startDate = this@toEntity.startDate
        endDate = this@toEntity.endDate
    }

fun CalendarDateEntity.toModel() =
    CalendarDate(
        serviceId = this.serviceId?.value ?: "",
        date = this.date ?: LocalDate(0, 1, 1),
        exceptionType = this.exceptionType ?: ScheduleAdjustmentType.SERVICE_REMOVED,
        id = this.id.value,
    )

fun CalendarDate.toEntity() =
    CalendarDateEntity.new {
        serviceId = this@toEntity.serviceId?.let { EntityID(it, CalendarsTable) }
        date = this@toEntity.date
        exceptionType = this@toEntity.exceptionType
    }

fun FeedInfoEntity.toModel() =
    FeedInfo(
        feedPublisherName = this.feedPublisherName ?: "",
        feedPublisherUrl = this.feedPublisherUrl ?: "",
        feedLang = this.feedLang ?: "",
        feedStartDate = this.feedStartDate ?: LocalDate(0, 1, 1),
        feedEndDate = this.feedEndDate ?: LocalDate(0, 1, 1),
        feedVersion = this.feedVersion ?: "",
        id = this.id.value,
    )

fun FeedInfo.toEntity() =
    FeedInfoEntity.new {
        feedPublisherName = this@toEntity.feedPublisherName
        feedPublisherUrl = this@toEntity.feedPublisherUrl
        feedLang = this@toEntity.feedLang
        feedStartDate = this@toEntity.feedStartDate
        feedEndDate = this@toEntity.feedEndDate
        feedVersion = this@toEntity.feedVersion
    }

fun RouteEntity.toModel() =
    Route(
        routeId = this.routeId.value,
        agencyId = this.agencyId?.value ?: "",
        routeShortName = this.routeShortName ?: "",
        routeLongName = this.routeLongName ?: "",
        routeDescription = this.routeDescription ?: "",
        routeType = this.routeType ?: 0,
        routeUrl = this.routeUrl ?: "",
        routeColor = this.routeColor ?: "",
        routeTextColor = this.routeTextColor ?: "",
    )

fun Route.toEntity() =
    RouteEntity.new {
        routeId = EntityID(this@toEntity.routeId, RoutesTable)
        agencyId = this@toEntity.agencyId?.let { EntityID(it, AgenciesTable) }
        routeShortName = this@toEntity.routeShortName
        routeLongName = this@toEntity.routeLongName
        routeDescription = this@toEntity.routeDescription
        routeType = this@toEntity.routeType
        routeUrl = this@toEntity.routeUrl
        routeColor = this@toEntity.routeColor
        routeTextColor = this@toEntity.routeTextColor
    }

fun ShapeEntity.toModel() =
    Shape(
        shapeId = this.shapeId,
        shapePtLat = this.shapePtLat ?: 0.0,
        shapePtLon = this.shapePtLon ?: 0.0,
        shapePtSequence = this.shapePtSequence ?: 0,
        shapeDistTraveled = this.shapeDistTraveled ?: 0.0,
        id = this.id.value,
    )

fun Shape.toEntity() =
    ShapeEntity.new {
        shapeId = this@toEntity.shapeId
        shapePtLat = this@toEntity.shapePtLat
        shapePtLon = this@toEntity.shapePtLon
        shapePtSequence = this@toEntity.shapePtSequence
        shapeDistTraveled = this@toEntity.shapeDistTraveled
    }

fun StopEntity.toModel() =
    Stop(
        stopId = this.stopId.value,
        stopCode = this.stopCode ?: 0,
        stopName = this.stopName ?: "",
        stopDesc = this.stopDesc ?: "",
        stopLat = this.stopLat ?: 0.0,
        stopLon = this.stopLon ?: 0.0,
        zoneId = this.zoneId ?: "",
        stopUrl = this.stopUrl ?: "",
        locationType = this.locationType,
        parentStation = this.parentStation ?: "",
    )

fun Stop.toEntity() =
    StopEntity.new {
        stopId = EntityID(this@toEntity.stopId, StopsTable)
        stopCode = this@toEntity.stopCode
        stopName = this@toEntity.stopName
        stopDesc = this@toEntity.stopDesc
        stopLat = this@toEntity.stopLat
        stopLon = this@toEntity.stopLon
        zoneId = this@toEntity.zoneId
        stopUrl = this@toEntity.stopUrl
        locationType = this@toEntity.locationType
        parentStation = this@toEntity.parentStation
    }

fun StopTimeEntity.toModel() =
    StopTime(
        tripId = this.tripId.value,
        arrivalTime = this.arrivalTime ?: LocalTime(0, 1, 1),
        departureTime = this.departureTime ?: LocalTime(0, 1, 1),
        stopId = this.stopId?.value ?: "",
        stopSequence = this.stopSequence ?: 0,
        stopHeadsign = this.stopHeadsign ?: "",
        pickupType = this.pickupType ?: 0,
        dropOffType = this.dropOffType ?: 0,
        timepoint = this.timepoint ?: 0,
        id = this.id.value,
    )

fun StopTime.toEntity() =
    StopTimeEntity.new {
        tripId = EntityID(this@toEntity.tripId, TripsTable)
        arrivalTime = this@toEntity.arrivalTime
        departureTime = this@toEntity.departureTime
        stopId = EntityID(this@toEntity.stopId, StopsTable)
        stopSequence = this@toEntity.stopSequence
        stopHeadsign = this@toEntity.stopHeadsign
        pickupType = this@toEntity.pickupType
        dropOffType = this@toEntity.dropOffType
        timepoint = this@toEntity.timepoint
    }

fun TripEntity.toModel() =
    Trip(
        routeId = this.routeId?.value ?: "",
        serviceId = this.serviceId?.value ?: "",
        tripId = this.tripId.value,
        tripHeadsign = this.tripHeadsign ?: "",
        tripShortName = this.tripShortName ?: "",
        directionId = this.directionId ?: 0,
        blockId = this.blockId ?: "",
        shapeId = this.shapeId,
    )

fun Trip.toEntity() =
    TripEntity.new {
        routeId = EntityID(this@toEntity.routeId, RoutesTable)
        serviceId = EntityID(this@toEntity.serviceId, CalendarsTable)
        tripId = EntityID(this@toEntity.tripId, TripsTable)
        tripHeadsign = this@toEntity.tripHeadsign
        tripShortName = this@toEntity.tripShortName
        directionId = this@toEntity.directionId
        blockId = this@toEntity.blockId
        shapeId = this@toEntity.shapeId
    }
