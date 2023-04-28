package io.thorib.framework


import io.thorib.framework.Thoribio.Action
import io.thorib.framework.Thoribio.Feature
import io.thorib.framework.Thoribio.FeatureComponent
import io.thorib.framework.Thoribio.State
import io.thorib.framework.Thoribio.StateReducer
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


internal class FeatureImp<ACTION , STATE , STATE_REDUCER> @Inject constructor(
    val component : FeatureComponent<ACTION , STATE , STATE_REDUCER>
) : Feature<ACTION , STATE>
    where
ACTION : Action ,
STATE : State ,
STATE_REDUCER : StateReducer<ACTION , STATE> {


    private val mutableStateFlow = MutableStateFlow(
        value = component.defaultState
    )

    private val scope = CoroutineScope(

        context = CoroutineName(name = "Feature[${component.defaultState::class.simpleName}]")
            + component.coroutineDispatcherProvider.default
            + SupervisorJob()
            + component.coroutineExceptionHandler
    )

    init {

        scope.launch(component.coroutineDispatcherProvider.io) {
            component.actionChannel.consumeAsFlow().collect { action ->
                mutableStateFlow.update {
                    component.stateReducer.reduce(
                        action = action ,
                        currentState = mutableStateFlow.value
                    )
                }
            }
        }

    }

    override fun use(action : () -> ACTION) : Boolean =
        component.actionChannel.trySend(
            element = action()
        ).isSuccess

    override val state : StateFlow<STATE> = mutableStateFlow.asStateFlow()

    override fun stop() = scope.cancel(
        message = "Stopping ${scope.coroutineContext}"
    )

}