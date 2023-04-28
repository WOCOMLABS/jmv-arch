package io.thorib.framework.di

import dagger.Binds
import dagger.Module
import io.thorib.framework.Thoribio.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Coroutine dispatcher provider module
 *
 * @constructor Create empty Coroutine dispatcher provider module
 */
@Module
interface CoroutineDispatcherProviderModule {


    /**
     * Provides coroutine dispatcher provider
     *
     * @param provider
     * @return
     */
    @Binds
    fun providesCoroutineDispatcherProvider(provider : CoroutineDispatcherProviderImp) : CoroutineDispatcherProvider

}


/**
 * Coroutine dispatcher provider imp
 *
 * @constructor Create empty Coroutine dispatcher provider imp
 */
class CoroutineDispatcherProviderImp @Inject constructor() :
    CoroutineDispatcherProvider {


    override val main : CoroutineDispatcher = Dispatchers.Main
    override val default : CoroutineDispatcher = Dispatchers.Default
    override val io : CoroutineDispatcher = Dispatchers.IO
    override val unconfined : CoroutineDispatcher = Dispatchers.Unconfined
}