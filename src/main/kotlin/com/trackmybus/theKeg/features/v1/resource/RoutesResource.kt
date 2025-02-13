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
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

@Suppress("unused")
@Serializable
enum class RouteType {
    TRAM,
    UNDERGROUND,
    RAIL,
    BUS,
}

@Resource("/route")
class Route(
    val parent: TheKeg.V1,
) {
    @Resource("/variants")
    class Variants(
        val parent: Route,
        val routeId: String? = null,
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
}

fun Application.routesRoutes() {
    routing {
        getRouteVariants()
        getRouteByType()
        getAllRoutes()
    }
}

fun Route.getRouteByType() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Route.ByType> { byTypeRequest ->
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

    get<Route.All> {
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

fun Route.getRouteVariants() {
    val scheduleUseCase: ScheduleUseCase by application.inject()

    get<Route.Variants> { variantsRequest ->
        val routeId = variantsRequest.routeId

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
