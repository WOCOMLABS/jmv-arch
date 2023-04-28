package io.thorib.service.di

import dagger.Binds
import dagger.Module
import io.thorib.domain.periodictable.PeriodicTableDomain.Service
import io.thorib.repository.di.PeriodicTableRepositoryModule
import io.thorib.service.PeriodicTableService

/**
 * Periodic table service module
 *
 * @constructor Create empty Periodic table service module
 */
@Module(
    includes = [
        PeriodicTableRepositoryModule::class ,
    ]
)
interface PeriodicTableServiceModule {

    @Binds
    fun providesPeriodicTableService(
        service : PeriodicTableService
    ) : Service
}
