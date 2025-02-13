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
import org.koin.ktor.ext.inject

@Resource("/trip")
class Trips(
    val parent: TheKeg.V1,
    val tripId: String? = null,
    val routeId: String? = null,
) {
    @Resource("/shapes")
    class Shapes(
        val parent: Trips,
    )
}

fun Application.tripsRoutes() {
    routing {
        getStopsForTrip()
        getShapesForTrip()
    }
}

fun Route.getStopsForTrip() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Trips> { request ->
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

fun Route.getShapesForTrip() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Trips.Shapes> { request ->
        val tripId = request.parent.tripId

        require(tripId != null) {
            call.respondError(
                statusCode = HttpStatusCode.BadRequest,
                errorCode = 400,
                message = "Trip ID is required",
            )
        }

        runCatching {
            scheduleUseCase.getShapesForTrip(tripId).getOrNull()
        }.onSuccess {
            call.respondSuccess(it)
        }.onFailure {
            call.respondError(
                statusCode = HttpStatusCode.InternalServerError,
                errorCode = 500,
                message = "Failed to fetch shapes for trip",
                details = it.message,
            )
        }
    }
}
