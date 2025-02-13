package com.trackmybus.theKeg.features.v1.resource

import com.trackmybus.theKeg.config.TheKeg
import com.trackmybus.theKeg.core.api.respondError
import com.trackmybus.theKeg.core.api.respondSuccess
import com.trackmybus.theKeg.features.v1.domain.usecases.ScheduleUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.resources.get
import io.ktor.server.resources.put
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import kotlin.getValue

@Resource("/gtfs")
class Gtfs(
    val parent: TheKeg.V1,
) {
    @Resource("/update")
    class Update(
        val parent: Gtfs,
    )

    @Resource("/verify")
    class Verify(
        val parent: Gtfs,
    )
}

fun Application.gtfsRoutes() {
    routing {
        updateTfiScheduleRoute()
    }
}

fun Route.updateTfiScheduleRoute() {
    val scheduleUseCase: ScheduleUseCase by application.inject()
    put<Gtfs.Update> {
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
