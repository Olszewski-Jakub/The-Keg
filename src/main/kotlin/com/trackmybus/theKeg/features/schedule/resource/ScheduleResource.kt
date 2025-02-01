package com.trackmybus.theKeg.features.schedule.resource

import com.trackmybus.theKeg.core.api.respondError
import com.trackmybus.theKeg.core.api.respondSuccess
import com.trackmybus.theKeg.features.schedule.domain.mapper.toDto
import com.trackmybus.theKeg.features.schedule.domain.repository.gtfs.GtfsScheduleRepository
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

@Resource("/schedule")
class Schedule {
    @Resource("/update")
    class Update(
        val parent: Schedule = Schedule(),
    ) {
        @Resource("/gtfs")
        class Gtfs(
            val parent: Update = Update(),
        ) {
            @Resource("/tfi")
            class Tfi(
                val parent: Gtfs = Gtfs(),
            )
        }
    }
}

fun Application.scheduleRoutes() {
    routing {
        updateTfiScheduleRoute()
    }
}

fun Route.updateTfiScheduleRoute() {
    val gtfsScheduleRepository by application.inject<GtfsScheduleRepository>()

    get<Schedule.Update.Gtfs.Tfi> {
        runCatching {
            gtfsScheduleRepository.fetchGtfsData()
        }.onSuccess {
            call.respondSuccess(it.getOrElse { null }?.toDto())
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
