package com.trackmybus.theKeg.config

import com.trackmybus.theKeg.features.schedule.resource.scheduleRoutes
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Resources)
    routing {
        scheduleRoutes()
    }
}
