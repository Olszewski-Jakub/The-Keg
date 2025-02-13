package com.trackmybus.theKeg.config

import com.trackmybus.theKeg.features.v1.resource.gtfsRoutes
import com.trackmybus.theKeg.features.v1.resource.routesRoutes
import com.trackmybus.theKeg.features.v1.resource.stopsRoutes
import com.trackmybus.theKeg.features.v1.resource.tripsRoutes
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

@Resource("/the-keg")
class TheKeg {
    @Resource("/v1")
    class V1(
        val parent: TheKeg,
    )

    @Resource("/docs")
    class Docs(
        val parent: TheKeg,
    )
}

fun Application.configureRouting() {
    install(Resources)

    routing {
        docsRoutes()
        gtfsRoutes()
        routesRoutes()
        tripsRoutes()
        stopsRoutes()
    }

    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
    }
}

fun Application.docsRoutes() {
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
        openAPI(path = "the-keg/docs")
        swaggerUI(path = "the-keg/docs")
    }
}
