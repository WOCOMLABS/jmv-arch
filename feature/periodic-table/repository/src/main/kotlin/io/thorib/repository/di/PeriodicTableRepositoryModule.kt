package io.thorib.repository.di

import dagger.Binds
import dagger.Module
import io.thorib.domain.periodictable.PeriodicTableDomain
import io.thorib.domain.periodictable.dto.PeriodicTableDTO
import io.thorib.framework.Thoribio
import io.thorib.repository.PeriodicTableRepository

/**
 * Periodic table repository module
 *
 * @constructor Create empty Periodic table repository module
 */
@Module(
    includes = [
        PeriodicTableHttpClientModule::class ,
    ]
)
interface PeriodicTableRepositoryModule {


    @Binds
    fun bindsPeriodicTableRepository(
        repository : PeriodicTableRepository
    ) : Thoribio.Repository<PeriodicTableDomain.Action, PeriodicTableDTO>
}


