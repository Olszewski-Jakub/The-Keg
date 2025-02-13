package com.trackmybus.theKeg.features.v1.resource

import com.trackmybus.theKeg.config.TheKeg
import com.trackmybus.theKeg.core.api.respondError
import com.trackmybus.theKeg.core.api.respondSuccess
import com.trackmybus.theKeg.features.v1.domain.usecases.ScheduleUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.routing
import kotlinx.datetime.LocalDate
import org.koin.ktor.ext.inject

@Resource("/stops")
class Stops(
    val parent: TheKeg.V1,
    val routeId: String? = null,
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

fun Application.stopsRoutes() {
    routing {
        getStopList()
        getDepartureTimesFromStop()
        getRoutesPassingThroughStop()
    }
}

fun Route.getStopList() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Stops.List> { listRequest ->

        val firstStopId = listRequest.firstStopId
        val endStopId = listRequest.endStopId
        val routeId = listRequest.parent.routeId

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

fun Route.getDepartureTimesFromStop() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Stops.DepartureTimes> { request ->
        val stopId = request.stopId
        val routeId = request.parent.routeId
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

fun Route.getRoutesPassingThroughStop() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Stops.PassingRoutes> { request ->
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
