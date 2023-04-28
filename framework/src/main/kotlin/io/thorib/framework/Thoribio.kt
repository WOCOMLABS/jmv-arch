package io.thorib.framework


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

/**
 * Thoribio d s l
 *
 * @constructor Create empty Thoribio d s l
 */
@Target(
    AnnotationTarget.CLASS ,
    AnnotationTarget.FUNCTION ,
    AnnotationTarget.PROPERTY
)
@DslMarker
annotation class ThoribioDSL


/**
 * Thoribio
 * A Multiplatform SDK library for building unidirectional architectures that
 * rely on coroutines for high performance concurrency.
 *
 * The library provides several key interfaces to define the components of an
 * unidirectional architecture, including:
 *
 * - DTO: A Data Transfer Object interface to define data objects that are
 * passed from the model to the service.
 *
 * - Action: An interface to define actions that are dispatched from the view
 * and handled by the feature.
 *
 * - State: An interface to define the state of the feature.
 *
 * - Service: A functional interface to define a service that handles an action
 * and returns a state.
 *
 * - Repository: A functional interface to define a repository that handles an
 * action and returns a DTO.
 *
 * - ActionDispatcher: A functional interface to define an action dispatcher
 * that dispatches an action to a service.
 *
 * - StateReducer: A functional interface to define a state reducer that reduces
 * an action and the current state to produce a new state.
 *
 * - SideEffect: A functional interface to define a side effect handler that
 * can be executed alongside a state reducer.
 *
 * - Feature: An interface to define a feature that contains a service, an
 * action dispatcher, and a state reducer. It also has methods for sending
 * actions, accessing the state, and cancelling and joining the coroutines.
 *
 */
@ThoribioDSL
object Thoribio {


    /**
     * DTO
     * A Data Transfer Object interface to define data objects that are passed from the model to the Service.
     *
     * Example usage:
     * ```
     * data class DashboardDTO(
     *   val id: Int
     *   val name: String
     * ) : DTO
     * ```
     */
    @ThoribioDSL
    interface DTO


    /**
     * Action
     * An interface to define actions that are dispatched from the view and handled by the feature.
     *
     * Example usage:
     * ```
     * sealed interface DashboardAction : Action {
     *
     *   object Initial : DashboardAction
     *   object Start : DashboardAction
     *   object Load : DashboardAction
     *   object Reload : DashboardAction
     *   object Exit : DashboardAction
     *
     * }
     * ```
     */
    @ThoribioDSL
    interface Action


    /**
     * State
     * An interface to define the state of the feature.
     *
     * Example usage:
     * ```
     * sealed interface DashboardState : State {
     *
     *   object Loading : DashboardState
     *   data class Error(val error : Exception) : DashboardState
     *   data class Success(val data : SomeConcreteObject) : DashboardState
     *
     * }
     * ```
     */
    @ThoribioDSL
    interface State


    /**
     * Service
     * A functional interface to define a service that concurrently handles an action and returns a state.
     *
     * Example usage:
     * ```
     * val dashboardService = Service<DashboardAction, DashboardState> { action ->
     *   // call your repo here
     *   DashboardState(SomeConcreteObject(id = "1234567890", name = "John Doe"))
     * }
     * ```
     *
     * @param ACTION The type of actions that the service can handle.
     * @param STATE The type of State that the service returns after handling an action.
     */
    @ThoribioDSL
    fun interface Service<ACTION , STATE> where ACTION : Action , STATE : State {


        /**
         * Use With
         * concurrently handles an action and return a Result state.
         *
         * @param action The action to handle.
         * @return The state produced by handling the action.
         */
        @ThoribioDSL
        suspend infix fun useWith(action : ACTION) : Result<STATE>
    }


    /**
     * Repository
     * A functional interface to define a repository that concurrently handles an action and returns a DTO.
     * Example usage:
     * ```
     * val dashboardRepository = Repository<DashboardAction, DashboardDTO> { action ->
     *  when (action) {
     *     DashboardAction.Load -> api.fetchDashboardData(...)
     *     DashboardAction.Reload ->  cache.fetchDashboardData(...)
     *     else -> cache.fetchDashboardData(...)
     *   }
     * }
     * ```
     *
     * @param ACTION The type of actions that the repository can handle.
     * @param REMOTE The type of data transfer object (DTO) that the repository returns after handling an action.
     *
     */
    @ThoribioDSL
    fun interface Repository<ACTION , REMOTE> where
    ACTION : Action ,
    REMOTE : DTO {


        /**
         * Interact With
         * Handles the specified action and returns a DTO.
         *
         * @param action The action to handle.
         * @return The DTO produced by handling the action.
         */
        @ThoribioDSL
        suspend infix fun interactWith(action : ACTION) : REMOTE

    }


    /**
     * ActionDispatcher
     * A functional interface to define an action dispatcher that dispatches actions to a service.
     *
     * Example usage:
     * ```
     * val dashboardActionDispatcher = ActionDispat¡cher<DashboardAction, DashboardState> { action, state ->
     *   when (action) {
     *     DashboardAction.Initial -> dashboardService.use(DashboardAction.Load)
     *     DashboardAction.Load -> dashboardService.use(DashboardAction.Reload)
     *     DashboardAction.Reload -> dashboardService.use(DashboardAction.Start)
     *     DashboardAction.Start -> dashboardService.use(DashboardAction.Load)
     *     DashboardAction.Exit -> state
     *   }
     * }
     * ```
     *
     * @param ACTION The type of actions that the action dispatcher can handle.
     * @param STATE The type of state that the action dispatcher returns after handling an action.
     */
    @ThoribioDSL
    fun interface ActionDispatcher<ACTION , STATE> where ACTION : Action , STATE : State {


        /**
         * Dispatch
         * Dispatches an action to a service and returns the resulting state.
         *
         * @param action The action to dispatch.
         * @param state The current state of the feature.
         * @return The new state of the feature after dispatching the action.
         */
        @ThoribioDSL
        suspend fun dispatch(
            action : ACTION ,
            state : STATE
        ) : STATE
    }


    /**
     * StateReducer
     * A functional interface to define a state reducer that reduces an action
     * and the current state to produce a new state.
     * Example usage with side effects:
     * ```
     *  class DashboardStateReducer(
     *      val dashboardSideEffect : DashboardSideEffect
     *  ) : StateReducer<DashboardAction, DashboardState> {
     *
     *      suspend fun reduce(action : DashboardAction, state:DashboardState) -> DashboardState {
     *          when (action) {
     *              DashboardAction.Start -> DashboardState.Loading
     *              DashboardAction.Load -> when (state) {
     *              is DashboardState.Loading -> state
     *              is DashboardState.Error -> DashboardState.Loading
     *              is DashboardState.Success -> state
     *              }
     *              DashboardAction.Reload -> DashboardState.Loading
     *              DashboardAction.Exit -> DashboardState.Success(
     *                  SomeConcreteObject(
     *                      id = "1234567890",
     *                      name = "John Doe"
     *                  )
     *              )
     *         }.also {
     *              dashboardSideEffect.execute(action)
     *         }
     *
     *      }
     *
     * }
     * ```
     *
     * @param ACTION The type of actions that the state reducer can handle.
     * @param STATE The type of state that the state reducer operates on.
     */
    @ThoribioDSL
    fun interface StateReducer<ACTION , STATE> where ACTION : Action , STATE : State {


        /**
         * Reduce
         * Processes an action and the current state to produce a new state.
         *
         * @param action The action to process.
         * @param currentState The current state.
         * @return The new state produced by processing the action and the current state.
         */
        @ThoribioDSL
        suspend fun reduce(
            action : ACTION ,
            currentState : STATE
        ) : STATE
    }


    /**
     * Side effect
     * A functional interface to define a state reducer that reduces an action
     * and the current state to produce a new state."Hidden side effect"
     * redirects here. For similar uses, see hidden variable.
     * In computer science, an operation, function or expression is said to have
     * a side effect if it modifies some state variable value(s) outside
     * its local environment, which is to say if it has any observable effect
     * other than its primary effect of returning a value to the invoker of the
     * operation. Example side effects include modifying a non-local variable,
     * modifying a static local variable, modifying a mutable argument passed
     * by reference, performing I/O or calling other functions with
     * side-effects.
     * In the presence of side effects, a program's behaviour may depend on
     * history; that is, the order of evaluation matters. Understanding and
     * debugging a function with side effects requires knowledge about the
     * context and its possible histories.
     *
     *
     * Example usage:
     *
     * ```
     * val dashboardSideEffect = DashboardSideEffect<DashboardAction> { action ->
     *      when (action) {
     *          DashboardAction.Start -> {
     *              //
     *              // (example) : notify metrics feature started
     *          }
     *          DashboardAction.Load -> {
     *
     *          }
     *          is DashboardState.Loading -> {
     *
     *          }
     *          is DashboardState.Error -> {
     *              // (example) : retry
     *              // (example) : if failed again navigate back
     *          }
     *          is DashboardState.Success -> {
     *
     *          }
     *     }
     *     DashboardAction.Reload -> {}
     *     DashboardAction.Exit -> {}
     *   }
     * }
     * @param ACTION
     * @constructor Create empty Side effect
     */
    @ThoribioDSL
    fun interface SideEffect<ACTION> where ACTION : Action {


        /**
         * Execute With
         * Executes a side effect based on the provided action.
         *
         * @param action The action that triggers the side effect.
         */
        @ThoribioDSL
        infix fun executeWith(
            action : ACTION ,
        )
    }


    /**
     * Feature - An interface representin a coherent piece of business logic in an unidirectional architecture. It encompasses all the components needed to handle actions and update state.
     *
     * @param ACTION - The type of actions that this feature can perform. Must implement the Action interface.
     * @param STATE - The type of state that this feature maintains. Must implement the State interface.
     *
     */
    @ThoribioDSL
    interface Feature<ACTION , STATE> where
    ACTION : Action ,
    STATE : State {


        /**
         * use - Performs the specified action.
         *
         * @param action - The action to perform, specified as a lambda expression.
         * @receiver - The receiver object to perform the action on.
         * @return - True if the action was acknowledged successfully, False otherwise.
         */
        @ThoribioDSL
        infix fun use(action : () -> ACTION) : Boolean


        /**
         * state - A flow representing the current state of the feature.
         */
        @ThoribioDSL
        val state : StateFlow<STATE>


        /**
         * stop - Stops the feature.
         */
        @ThoribioDSL
        fun stop()

        companion object {


            /**
             * Invoke - Creates a Feature instance with the specified FeatureComponent.
             *
             * @param component - The FeatureComponent used to create the Feature instance. It includes the Action, State, and StateReducer implementations.
             * @return A Feature instance that encompasses all the components needed to handle actions and update state.
             */
            @ThoribioDSL
            operator fun <ACTION : Action , STATE : State , STATE_REDUCER : StateReducer<ACTION , STATE>> invoke(component : FeatureComponent<ACTION , STATE , STATE_REDUCER>) : Feature<ACTION , STATE> =
                FeatureImp(component = component)

        }
    }


    /**
     * FeatureComponent
     * A feature component is a high-level abstraction that represents a standalone feature in an unidirectional architecture.
     * A feature component is made up of several key parts:
     * - A `Service` instance that handles actions and returns states.
     * - An `ActionDispatcher` instance that dispatches actions to the service.
     * - A `StateReducer` instance that reduces actions and the current state to produce a new state.
     *
     * By encapsulating all the components of a feature in a single interface, FeatureComponent makes it easy to create, test, and reuse features throughout an application.
     *
     * Example usage:
     * ```
     * // Create a feature component
     * class DashboardFeatureComponent(
     *   private val dashboardService: Service<DashboardAction, DashboardState>,
     *   private val dashboardActionDispatcher: ActionDispatcher<DashboardAction>,
     *   private val dashboardStateReducer: StateReducer<DashboardState, DashboardAction>
     * ) : FeatureComponent<DashboardAction, DashboardState> {
     *
     *   override val service: Service<DashboardAction, DashboardState> = dashboardService
     *   override val actionDispatcher: ActionDispatcher<DashboardAction> = dashboardActionDispatcher
     *   override val stateReducer: StateReducer<DashboardState, DashboardAction> = dashboardStateReducer
     *
     * }

     * ```
     *
     * @param ACTION The type of actions that the feature can handle.
     * @param STATE The type of state that the feature produces.
     * @param STATE_REDUCER The type of state reducer that the feature uses to process the changes emitted from the multiple .
     */
    @ThoribioDSL
    interface FeatureComponent<ACTION , STATE , STATE_REDUCER> where
    ACTION : Action ,
    STATE : State ,
    STATE_REDUCER : StateReducer<ACTION , STATE> {


        val coroutineDispatcherProvider : CoroutineDispatcherProvider

        val actionChannel : Channel<ACTION>

        val defaultState : STATE

        val coroutineExceptionHandler : CoroutineExceptionHandler

        val stateReducer : STATE_REDUCER

    }


    /**
     * CoroutineDispatcherProvider interface provides a set of coroutine dispatchers that can be used
     * to run coroutines in different contexts.
     *
     */
    @ThoribioDSL
    interface CoroutineDispatcherProvider {


        val main : CoroutineDispatcher

        val default : CoroutineDispatcher

        val io : CoroutineDispatcher

        val unconfined : CoroutineDispatcher
    }
}

