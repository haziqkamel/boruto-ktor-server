package com.haziqkamel.plugins

import com.haziqkamel.routes.getAllHeroes
import com.haziqkamel.routes.root
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        root()
        getAllHeroes()

        // Implementation of static resources
        static("/images") {
            resources("images")
        }
    }
}
