package com.trackmybus.theKeg.config

import com.trackmybus.theKeg.features.schedule.resource.scheduleRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    install(Resources)
    routing {
        openAPI(path = "docs")
    }
    routing {
        swaggerUI(path = "docs")
    }

    routing {
        scheduleRoutes()
    }
}
