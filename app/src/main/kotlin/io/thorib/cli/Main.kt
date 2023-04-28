package io.thorib.cli

import io.thorib.di.component.DaggerPeriodicTableComponent
import io.thorib.domain.periodictable.PeriodicTableDomain
import io.thorib.domain.periodictable.PeriodicTableDomain.State.Failure
import io.thorib.domain.periodictable.PeriodicTableDomain.State.Initialized
import io.thorib.domain.periodictable.PeriodicTableDomain.State.Loading
import io.thorib.domain.periodictable.PeriodicTableDomain.State.Success
import kotlinx.coroutines.runBlocking


suspend fun main()  = runBlocking {


    val serviceResult = DaggerPeriodicTableComponent
        .builder()
        .build()
        .periodicTableService().apply {


        }
        .useWith(PeriodicTableDomain.Action.Data)


    serviceResult.fold(
        onSuccess = { state ->
           when(state) {
               is Failure  -> println(state)
               Initialized -> println(state)
               Loading     -> println(state)
               is Success  -> state.elements.forEach { element ->
                   println(element)
               }
           }
        } ,
        onFailure = { error ->
            println(error)
        }
    )

}