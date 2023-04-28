package io.thorib.domain.periodictable

import io.thorib.framework.Thoribio
import io.thorib.framework.ThoribioDSL


@ThoribioDSL
object PeriodicTableDomain {


    @ThoribioDSL
    interface DTO : Thoribio.DTO


    @ThoribioDSL
    sealed interface Action : Thoribio.Action {


        /**
         * Initial
         *
         * @constructor Create empty Initial
         */
        object Initial : Action


        /**
         * Load
         *
         * @constructor Create empty Load
         */
        object Load : Action


        /**
         * Data
         *
         * @constructor Create empty Data
         */
        object Data : Action
    }


    @ThoribioDSL
    sealed interface State : Thoribio.State {
        /**
         * Initialized
         *
         * @constructor Create empty Initialized
         */
        object Initialized : State


        /**
         * Loading
         *
         * @constructor Create empty Loading
         */
        object Loading : State


        /**
         * Success
         *
         * @property elements
         * @constructor Create empty Success
         */
        data class Success(val elements : List<Element>) : State {


            /**
             * Element
             *
             * @property symbol
             * @constructor Create empty Element
             */
            data class Element(
                val symbol : String ,
            )
        }


        /**
         * Failure
         *
         * @property error
         * @constructor Create empty Failure
         */
        data class Failure(
            val error : Throwable ,
        ) : State
    }


    @ThoribioDSL
    fun interface Service : Thoribio.Service<Action , State>


    @ThoribioDSL
    fun interface ActionDispatcher : Thoribio.ActionDispatcher<Action , State>


    @ThoribioDSL
    fun interface StateReducer : Thoribio.StateReducer<Action , State>


    @ThoribioDSL
    fun interface SideEffect : Thoribio.SideEffect<Action>


    @ThoribioDSL
    interface Feature : Thoribio.Feature<Action , State>

}


