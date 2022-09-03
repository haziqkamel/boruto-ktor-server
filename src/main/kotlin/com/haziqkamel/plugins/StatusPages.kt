package com.haziqkamel.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import javax.naming.AuthenticationException

fun Application.configureStatusPages() {
    install(StatusPages) {
//        status(HttpStatusCode.NotFound) { call, status ->
//            call.respond(
//                message = "Page Not Found!",
//                status = status
//            )
//        }
//
//        exception<AuthenticationException> { call, _ ->
//            call.respond(
//                message = "Not Authorized!",
//                status = HttpStatusCode.Unauthorized
//            )
//        }
    }
}