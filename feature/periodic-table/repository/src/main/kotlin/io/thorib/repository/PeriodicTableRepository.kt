package io.thorib.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.request.get
import io.ktor.events.EventDefinition
import io.ktor.http.isSuccess
import io.thorib.domain.periodictable.PeriodicTableDomain
import io.thorib.domain.periodictable.dto.PeriodicTableDTO
import io.thorib.framework.Thoribio.Repository
import javax.inject.Inject


/**
 * Periodic table repository imp
 *
 * @property httpClient
 * @constructor Create empty Periodic table repository imp
 */
class PeriodicTableRepository @Inject constructor(
    private val httpClient : HttpClient ,
) : Repository<PeriodicTableDomain.Action , PeriodicTableDTO> {


    override suspend infix fun interactWith(action : PeriodicTableDomain.Action) = httpClient.run {

        config {

            HttpResponseValidator {
                handleResponseExceptionWithRequest { error , request ->
                    // TODO: [1]
                }

                validateResponse { response ->
                    response.status.isSuccess()
                    // TODO: [2]
                }
            }

        }
        monitor.raise(
            definition = EventDefinition<Event>() ,
            value = Event.OK
        )

        get("/periodic-table").body<PeriodicTableDTO>()
    }

    sealed interface Event {

        object OK : Event
        object Fail : Event
    }
}