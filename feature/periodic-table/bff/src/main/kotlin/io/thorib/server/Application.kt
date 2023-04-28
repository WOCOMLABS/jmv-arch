package io.thorib.server

import io.ktor.server.application.Application
import io.ktor.server.cio.EngineMain
import io.thorib.server.plugins.configureMonitoring
import io.thorib.server.plugins.configureSerialization

fun main(args : Array<String>) : Unit = EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureMonitoring()
}
