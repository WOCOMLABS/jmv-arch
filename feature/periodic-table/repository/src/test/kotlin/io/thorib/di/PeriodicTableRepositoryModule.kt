package io.thorib.di

import dagger.Component
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.http.ContentType.Application
import io.ktor.http.HttpHeaders
import io.ktor.http.fullPath
import io.ktor.http.headers
import io.ktor.serialization.gson.gson
import io.thorib.mock.readFile

/**
 * Periodic table repository test component
 *
 * @constructor Create empty Periodic table repository test component
 */
@Component(
    modules = [
        HttpTestModule::class
    ]
)
interface PeriodicTableRepositoryTestComponent {


    val testHttpClient : HttpClient


}


/**
 * Http test module
 *
 * @constructor Create empty Http test module
 */
@Module
class HttpTestModule {


    /**
     * Provides http
     *
     * @return
     */
    @Provides
    fun providesHttp() : HttpClient = HttpClient(MockEngine) {

        engine {
            addHandler { request ->

               when (request.url.fullPath) {
                    "/periodic-table" -> respond(
                        content = "elements.json".readFile(),
                        headers = headers { append(
                            name = HttpHeaders.ContentType ,
                            value = Application.Json.toString()
                        ) }
                    )
                else -> error("Unhandled ${request.url.fullPath}")
                }
            }

        }
        developmentMode = true

        defaultRequest {
            host = "0.0.0.0"
            port = 8080
            bearerAuth("")
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            gson {
                disableHtmlEscaping()
            }

        }

        install(HttpTimeout) {
            requestTimeoutMillis = 1_999
        }
    }
}