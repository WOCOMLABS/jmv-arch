package io.thorib.service

import io.thorib.domain.periodictable.PeriodicTableDomain
import io.thorib.domain.periodictable.PeriodicTableDomain.Action
import io.thorib.domain.periodictable.PeriodicTableDomain.State
import io.thorib.domain.periodictable.PeriodicTableDomain.State.Initialized
import io.thorib.domain.periodictable.PeriodicTableDomain.State.Loading
import io.thorib.domain.periodictable.PeriodicTableDomain.State.Success
import io.thorib.domain.periodictable.PeriodicTableDomain.State.Success.Element
import io.thorib.domain.periodictable.dto.PeriodicTableDTO
import io.thorib.framework.Thoribio.Repository
import javax.inject.Inject

/**
 * Periodic table service
 *
 * @property repository
 * @constructor Create empty Periodic table service
 */
class PeriodicTableService @Inject constructor(
    private val repository : Repository<Action , PeriodicTableDTO>
) : PeriodicTableDomain.Service{


    override suspend fun useWith(action : Action) : Result<State> = runCatching {

        when (action) {
            Action.Data    -> Success(
                elements = repository
                    .interactWith(action)
                    .elementDTOS
                    .map { elementDTO ->
                        Element(symbol = elementDTO.symbol)
                    }
            )

            PeriodicTableDomain.Action.Initial -> Initialized
            PeriodicTableDomain.Action.Load    -> Loading
        }
    }.fold(
        onFailure = { error ->
            Result.failure(error)
        } ,
        onSuccess = { state ->
            Result.success(state)
        } ,
    )
}