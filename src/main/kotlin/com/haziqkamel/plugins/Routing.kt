package com.haziqkamel.plugins

import com.haziqkamel.routes.root
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        root()
    }
}
