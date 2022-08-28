package com.haziqkamel.routes

import com.haziqkamel.models.ApiResponse
import com.haziqkamel.repository.HeroRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.lang.NumberFormatException

fun Route.getAllHeroes() {
    //Dependency Injection
    // if type HeroRepository, inject HeroRepositoryImplementation
    val heroRepository: HeroRepository by inject()

    get("/boruto/heroes") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5)

            // Call HeroImplementation
            val apiResponse = heroRepository.getAllHeroes(page)
            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )

        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(
                    success = false,
                    message = "Only numbers are allowed."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(
                    success = false,
                    message = "Heroes not found."),
                status = HttpStatusCode.NotFound
            )
        }
    }
}