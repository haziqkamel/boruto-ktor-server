package com.haziqkamel.plugins

import com.haziqkamel.routes.getAllHeroes
import com.haziqkamel.routes.root
import com.haziqkamel.routes.searchHero
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import javax.naming.AuthenticationException

fun Application.configureRouting() {

    routing {
        root()
        getAllHeroes()
        searchHero()

        // Implementation of static resources
        static("/images") {
            resources("images")
        }
    }
}
