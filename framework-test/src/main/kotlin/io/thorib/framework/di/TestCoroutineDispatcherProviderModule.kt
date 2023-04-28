package io.thorib.framework.di

import dagger.Binds
import dagger.Module
import io.thorib.framework.Thoribio.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Inject


/**
 * Test coroutine dispatcher provider module
 *
 * @constructor Create empty Test coroutine dispatcher provider module
 */
@Module
interface TestCoroutineDispatcherProviderModule {


    /**
     * Provides coroutine dispatcher provider
     *
     * @param provider
     * @return
     */
    @Binds
    fun providesCoroutineDispatcherProvider(provider : CoroutineDispatcherProviderTestImp) : CoroutineDispatcherProvider

}


/**
 * Coroutine dispatcher provider test imp
 *
 * @constructor Create empty Coroutine dispatcher provider test imp
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineDispatcherProviderTestImp @Inject constructor() :
    CoroutineDispatcherProvider {


    override val main : CoroutineDispatcher = StandardTestDispatcher()
    override val default : CoroutineDispatcher = StandardTestDispatcher()
    override val io : CoroutineDispatcher = StandardTestDispatcher()
    override val unconfined : CoroutineDispatcher = UnconfinedTestDispatcher()
}