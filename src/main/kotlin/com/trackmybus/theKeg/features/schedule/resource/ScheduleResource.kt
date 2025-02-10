package com.trackmybus.theKeg.features.schedule.resource

import com.trackmybus.theKeg.core.api.respondError
import com.trackmybus.theKeg.core.api.respondSuccess
import com.trackmybus.theKeg.features.schedule.domain.usecases.ScheduleUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.routing
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

@Serializable
enum class RouteType {
    TRAM,
    UNDERGROUND,
    RAIL,
    BUS,
}

@Resource("/schedule")
class Schedule {
    @Resource("/update")
    class Update(
        val parent: Schedule,
    ) {
        @Resource("/gtfs")
        class Gtfs(
            val parent: Update,
        ) {
            @Resource("/tfi")
            class Tfi(
                val parent: Gtfs,
            )
        }
    }

    @Resource("/route")
    class Route(
        val parent: Schedule,
        val routeId: String? = null,
    ) {
        @Resource("/variants")
        class Variants(
            val parent: Route,
        )

        @Resource("/all")
        class All(
            val parent: Route,
        )

        @Resource("/byType")
        class ByType(
            val parent: Route,
            val type: RouteType? = null,
        )

        @Resource("/trip")
        class Trips(
            val parent: Route,
            val tripId: String? = null,
        )

        @Resource("/stops")
        class Stops(
            val parent: Route,
        ) {
            @Resource("/list")
            class List(
                val parent: Stops,
                val firstStopId: String? = null,
                val endStopId: String? = null,
            )

            @Resource("/departureTimes")
            class DepartureTimes(
                val parent: Stops,
                val stopId: String? = null,
                val date: LocalDate? = null,
            )

            @Resource("/passingRoutes")
            class PassingRoutes(
                val parent: Stops,
                val stopId: String? = null,
            )
        }
    }
}

fun Application.scheduleRoutes() {
    routing {
        updateTfiScheduleRoute()
        getRouteVariants()
        getStopList()
        getRouteByType()
        getAllRoutes()
        getDepartureTimesFromStop()
        getStopsForTrip()
        getRoutesPassingThroughStop()
    }
}

fun Route.updateTfiScheduleRoute() {
    val scheduleUseCase: ScheduleUseCase by application.inject()
    get<Schedule.Update.Gtfs.Tfi> {
        runCatching {
            scheduleUseCase.fetchAndSaveGtfsData()
        }.onSuccess {
            call.respondSuccess("GTFS data fetched and saved successfully")
        }.onFailure {
            call.respondError(
                statusCode = HttpStatusCode.InternalServerError,
                errorCode = 500,
                message = "Failed to fetch GTFS data",
                details = it.message,
            )
        }
    }
}

fun Route.getRouteVariants() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Schedule.Route.Variants> { variantsRequest ->
        val routeId = variantsRequest.parent.routeId

        require(routeId != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "Route ID is required",
            )
        }

        runCatching {
            scheduleUseCase.getRouteDirections(routeId)
        }.onSuccess {
            call.respondSuccess(it.getOrNull())
        }.onFailure {
            call.respondError(
                statusCode = HttpStatusCode.InternalServerError,
                errorCode = 500,
                message = "Failed to fetch route directions",
                details = it.message,
            )
        }
    }
}

fun Route.getStopList() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Schedule.Route.Stops.List> { listRequest ->

        val firstStopId = listRequest.firstStopId
        val endStopId = listRequest.endStopId
        val routeId = listRequest.parent.parent.routeId

        require(firstStopId != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "First stop ID is required",
            )
        }

        require(endStopId != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "End stop ID is required",
            )
        }

        require(routeId != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "Route ID is required",
            )
        }

        listRequest.parent
            .runCatching {
                scheduleUseCase.getStopsForGivenRoute(
                    routeId = routeId,
                    firstStopId = firstStopId,
                    endStopId = endStopId,
                )
            }.onSuccess {
                call.respondSuccess(it.getOrNull())
            }.onFailure {
                call.respondError(
                    statusCode = HttpStatusCode.InternalServerError,
                    errorCode = 500,
                    message = "Failed to fetch stops for route",
                    details = it.message,
                )
            }
    }
}

fun Route.getRouteByType() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Schedule.Route.ByType> { byTypeRequest ->
        val routeType = byTypeRequest.type

        require(routeType != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "Route type is required",
            )
        }

        byTypeRequest.parent
            .runCatching {
                scheduleUseCase.getRouteByType(routeType).getOrNull()
            }.onSuccess {
                call.respondSuccess(it)
            }.onFailure {
                call.respondError(
                    statusCode = HttpStatusCode.InternalServerError,
                    errorCode = 500,
                    message = "Failed to fetch routes by type",
                    details = it.message,
                )
            }
    }
}

fun Route.getAllRoutes() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Schedule.Route.All> {
        runCatching {
            scheduleUseCase.getAllRoutes().getOrNull()
        }.onSuccess {
            call.respondSuccess(it)
        }.onFailure {
            call.respondError(
                statusCode = HttpStatusCode.InternalServerError,
                errorCode = 500,
                message = "Failed to fetch all routes",
                details = it.message,
            )
        }
    }
}

fun Route.getDepartureTimesFromStop() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Schedule.Route.Stops.DepartureTimes> { request ->
        val stopId = request.stopId
        val routeId = request.parent.parent.routeId
        val date = request.date
        require(stopId != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "Stop ID is required",
            )
        }

        require(date != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "Date is required",
            )
        }

        require(routeId != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "Route ID is required",
            )
        }

        runCatching {
            scheduleUseCase
                .getAllDepartureTimesForStop(
                    stopId = stopId,
                    routeId = routeId,
                    dateTime = date,
                ).getOrNull()
        }.onSuccess {
            call.respondSuccess(it)
        }.onFailure {
            call.respondError(
                statusCode = HttpStatusCode.InternalServerError,
                errorCode = 500,
                message = "Failed to fetch departure times",
                details = it.message,
            )
        }
    }
}

fun Route.getStopsForTrip() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Schedule.Route.Trips> { request ->
        val tripId = request.tripId

        require(tripId != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "Trip ID is required",
            )
        }

        runCatching {
            scheduleUseCase.getStopsForTrip(tripId = tripId).getOrNull()
        }.onSuccess {
            call.respondSuccess(it)
        }.onFailure {
            call.respondError(
                statusCode = HttpStatusCode.InternalServerError,
                errorCode = 500,
                message = "Failed to fetch stops for trip",
                details = it.message,
            )
        }
    }
}

fun Route.getRoutesPassingThroughStop() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Schedule.Route.Stops.PassingRoutes> { request ->
        val stopId = request.stopId

        require(stopId != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "Stop ID is required",
            )
        }

        runCatching {
            scheduleUseCase.getRoutesPassingThroughStop(stopId)
        }.onSuccess {
            call.respondSuccess(it.getOrNull())
        }.onFailure {
            call.respondError(
                statusCode = HttpStatusCode.InternalServerError,
                errorCode = 500,
                message = "Failed to fetch routes passing through stop",
                details = it.message,
            )
        }
    }
}
