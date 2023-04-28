package io.thorib.repository

import io.thorib.di.DaggerPeriodicTableRepositoryTestComponent
import io.thorib.di.PeriodicTableRepositoryTestComponent
import io.thorib.domain.periodictable.PeriodicTableDomain.Action
import io.thorib.domain.periodictable.dto.PeriodicTableDTO
import io.thorib.framework.Thoribio.Repository
import io.thorib.framework.test.testThat
import io.thorib.mock.toInstance
import org.junit.After
import org.junit.Before
import org.junit.Test


class PeriodicTableRepositoryTest : PeriodicTableRepositoryTestComponent by DaggerPeriodicTableRepositoryTestComponent.create() {


    lateinit var SUT : Repository<Action , PeriodicTableDTO>


    @Before
    fun setUp() {
        SUT = PeriodicTableRepository(
            httpClient = testHttpClient
        )
    }


    @After
    fun tearDown() {
        testHttpClient.close()
    }


    @Test
    fun getElements() = SUT.testThat(
        action = Action.Load ,
        remote = "elements.json".toInstance<PeriodicTableDTO>()
    )

}