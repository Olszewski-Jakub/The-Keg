package com.trackmybus.theKeg.config

import com.trackmybus.theKeg.features.v1.schedule.resource.scheduleRoutes
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

@Resource("/the-keg")
class TheKeg {
    @Resource("/v1")
    class V1(
        val patent: TheKeg,
    )
}

fun Application.configureRouting() {
    install(Resources)

    routing {
        scheduleRoutes()
    }

    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
    }
}
