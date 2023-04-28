package io.thorib.server.plugins

import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.thorib.domain.periodictable.dto.PeriodicTableDTO
import io.thorib.mock.toInstance

/**
 * Configure serialization
 *
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            disableHtmlEscaping()
        }
    }
    routing {
        get("/periodic-table") {
            call.respond("elements.json".toInstance<PeriodicTableDTO>())
        }
    }
}
