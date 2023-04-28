package io.thorib.di.component

import dagger.Component
import io.thorib.domain.periodictable.PeriodicTableDomain
import io.thorib.domain.periodictable.dto.PeriodicTableDTO
import io.thorib.framework.Thoribio
import io.thorib.repository.di.PeriodicTableRepositoryModule
import io.thorib.service.di.PeriodicTableServiceModule

@Component(
    modules = [
        PeriodicTableServiceModule::class ,
    ]
)
interface PeriodicTableComponent {


    fun periodicTableService() : PeriodicTableDomain.Service

    fun periodicTableRepository() : Thoribio.Repository<PeriodicTableDomain.Action , PeriodicTableDTO>
}


