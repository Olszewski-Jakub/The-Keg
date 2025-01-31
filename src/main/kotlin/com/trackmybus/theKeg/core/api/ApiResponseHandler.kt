package com.trackmybus.theKeg.core.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend inline fun <reified T> ApplicationCall.respondSuccess(data: T) {
    respondText { Json.encodeToString(ApiResponse.Success(data)) }
}

suspend fun ApplicationCall.respondError(
    statusCode: HttpStatusCode,
    errorCode: Int,
    message: String,
    details: String? = null
) {
    respondText(
        text = Json.encodeToString(ApiResponse.Error(errorCode, message, details)),
        contentType = ContentType.Application.Json,
        status = statusCode
    )
}