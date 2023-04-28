@file:OptIn(ExperimentalCoroutinesApi::class)

package io.thorib.framework.test

import io.thorib.framework.Thoribio
import io.thorib.framework.Thoribio.Action
import io.thorib.framework.Thoribio.Repository
import io.thorib.framework.Thoribio.State
import io.thorib.framework.Thoribio.StateReducer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.jvm.Throws


/**
 * set of generic test functions that can be used to test
 * Thoribio components in a multiplatform environment.
 * The library provides the following functions:
 * testThat: Tests a Thoribio component such as a service, reducer or repository
 * by providing an action and an expected result.
 * If the component does not produce the expected result, an AssertionError
 * is thrown.
 * verify: A helper function that can be used to assert a condition is true.
 * If the condition is false, an AssertionError is thrown with a custom message.
 */


/**
 * Tests a [StateReducer] by providing the [currentState], [action] and the [expectedState].
 * If the reducer does not produce the expected state, an [AssertionError] is thrown.
 *
 * Example usage:
 * ```
 * myReducer.testThat(
 *     currentState = InitialState,
 *     action = SomeAction,
 *     expectedState = UpdatedState
 * )
 * ```
 */
@ThoribioTestDSL
@Throws(AssertionError::class)
suspend fun <ACTION , STATE , REDUCER> REDUCER.testThat(
    currentState : STATE ,
    action : ACTION ,
    expectedState : STATE ,
) where
    ACTION : Action ,
    STATE : State ,
    REDUCER : StateReducer<ACTION , STATE> = runTest {
    val newState = reduce(action , currentState)
    verify(newState == expectedState) {
        "Reducer test failed: $currentState + $action should result in $expectedState, but resulted in $newState"
    }
}


/**
 * Tests a [Thoribio.Service] by providing the [action] and the [expectedState].
 * If the service does not return the expected state, an [AssertionError] is thrown.
 *
 * Example usage:
 * ```
 * myService.testThat(
 *     action = SomeAction,
 *     expectedState = ExpectedState
 * )
 * ```
 */
@OptIn(ExperimentalCoroutinesApi::class)
@ThoribioTestDSL
@Throws(AssertionError::class)
suspend fun <ACTION , STATE , SERVICE> SERVICE.testThat(
    action : ACTION ,
    expectedState : STATE ,
) where
    ACTION : Action ,
    STATE : State ,
    SERVICE : Thoribio.Service<ACTION , STATE> = runTest {
    val result = useWith(action)
    verify(result == expectedState) {
        "Service test failed: $action should return $expectedState, but returned $result"
    }
}


/**
 * Tests a [Repository] by providing the [action] and the expected [remote] state.
 * If the repository does not return the expected remote state, an [AssertionError] is thrown.
 *
 * Example usage:
 * ```
 * myRepository.testThat(
 *     action = SomeAction,
 *     remote = ExpectedRemoteState
 * )
 * ```
 */

@ThoribioTestDSL
@Throws(AssertionError::class)
fun <ACTION , REMOTE > Repository<ACTION , REMOTE>.testThat(
    action : ACTION ,
    remote : REMOTE ,
) where
    ACTION : Action ,
    REMOTE : Thoribio.DTO
   = runTest {

    val result = interactWith(action)
    verify(result == remote) {
        "Repository test failed: $action should return $remote, but returned $result"
    }
}


/**
 * A helper function to assert a [value] is true. If the [value] is false, an [AssertionError] is thrown with
 * the provided [lazyMessage].
 *
 * Example usage:
 * ```
 * ThoribioTest.verify(someCondition) { "Assertion failed: someCondition should be true" }
 * ```
 */
@ThoribioTestDSL
@Throws(AssertionError::class)
fun verify(
    value : Boolean ,
    lazyMessage : () -> String
) {
    if (! value) {
        throw AssertionError(lazyMessage())
    }
}
