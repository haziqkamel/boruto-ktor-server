package com.haziqkamel

import com.haziqkamel.models.ApiResponse
import com.haziqkamel.repository.HeroRepository
import com.haziqkamel.repository.NEXT_PAGE_KEY
import com.haziqkamel.repository.PREV_PAGE_KEY
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    // Inject Repo
    private val heroRepository: HeroRepository by inject(HeroRepository::class.java)

    @Test
    fun `access root endpoint, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                assertEquals(
                    expected = "Welcome to Boruto API!",
                    actual = response.content
                )
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {

            val pages = 1..5
            val heroes = listOf(
                heroRepository.page1,
                heroRepository.page2,
                heroRepository.page3,
                heroRepository.page4,
                heroRepository.page5,
            )
            pages.forEach { page ->
                handleRequest(HttpMethod.Get, "/boruto/heroes?page=$page").apply {
                    println("Current Page: $page")
                    assertEquals(
                        expected = HttpStatusCode.OK,
                        actual = response.status()
                    )

                    val expected = ApiResponse(
                        success = true,
                        message = "Success",
                        previousPage = calculatePage(page)[PREV_PAGE_KEY],
                        nextPage = calculatePage(page)[NEXT_PAGE_KEY],
                        heroes = heroes[page - 1]
                    )

                    val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                    println("PREV PAGE: ${calculatePage(page = page)[PREV_PAGE_KEY]}")
                    println("NEXT PAGE: ${calculatePage(page = page)[NEXT_PAGE_KEY]}")
                    println("HEROES: ${heroes[page - 1]}")
                    assertEquals(
                        expected = expected,
                        actual = actual
                    )
                }
            }
        }
    }

    private fun calculatePage(page: Int): Map<String, Int?> {
        var prevPage: Int? = page
        var nextPage: Int? = page

        if (page in 1..4) {
            nextPage = nextPage?.plus(1)
        }

        if (page in 2..5) {
            prevPage = prevPage?.minus(1)
        }

        if (page == 1) {
            prevPage = null
        }

        if (page == 5) {
            nextPage = null
        }

        return mapOf(PREV_PAGE_KEY to prevPage, NEXT_PAGE_KEY to nextPage)
    }

    @ExperimentalSerializationApi
    @Test
    fun `access all heroes endpoint, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes?page=2").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                val expected = ApiResponse(
                    success = true,
                    message = "Success",
                    previousPage = 1,
                    nextPage = 3,
                    heroes = heroRepository.page2,
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                assertEquals(
                    expected = expected,
                    actual = actual,
                )
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access all heroes endpoint, query non existing page number, assert error`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes?page=6").apply {
                assertEquals(
                    expected = HttpStatusCode.BadRequest,
                    actual = response.status()
                )
                val expected = ApiResponse(
                    success = false,
                    message = "Heroes not found.",
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                assertEquals(
                    expected = expected,
                    actual = actual,
                )
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access all heroes endpoint, query invalid page number, assert error`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes?page=invalid").apply {
                assertEquals(
                    expected = HttpStatusCode.BadRequest,
                    actual = response.status()
                )
                val expected = ApiResponse(
                    success = false,
                    message = "Only numbers are allowed.",
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                assertEquals(
                    expected = expected,
                    actual = actual,
                )
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access search heroes endpoint, query hero name, assert single hero result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=sas").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes.size
                assertEquals(
                    expected = 1,
                    actual = actual,
                )
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access search heroes endpoint, query hero name, assert multiple hero result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=sa").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes.size
                assertEquals(
                    expected = 3,
                    actual = actual,
                )
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access search heroes endpoint, query an non existing hero, assert empty list as a result`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes/search?name=unknown").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )

                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes
                assertEquals(
                    expected = emptyList(),
                    actual = actual,
                )
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access non existing endpoint, assert not found`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/unknown").apply {
                assertEquals(
                    expected = HttpStatusCode.NotFound,
                    actual = response.status()
                )

                assertEquals(
                    expected = "Page Not Found!",
                    actual = response.content,
                )
            }
        }
    }


}