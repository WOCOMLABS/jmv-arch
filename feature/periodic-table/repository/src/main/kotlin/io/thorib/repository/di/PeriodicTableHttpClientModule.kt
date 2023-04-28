package io.thorib.repository.di

import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.serialization.gson.gson


@Module
class PeriodicTableHttpClientModule {


    @Provides
    fun providesHttpClient() : HttpClient = HttpClient(OkHttp) {

        developmentMode = true

        defaultRequest {
            host = "0.0.0.0"
            port = 8080
            bearerAuth("")
        }

        engine {

            addNetworkInterceptor { chain ->
                chain.request().newBuilder().header("some" , "other")
                chain.proceed(chain.request())
            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            gson {
                disableHtmlEscaping()
            }
        }

//        install(Resources){
//            serializersModule
//        }

        install(HttpTimeout) {
            requestTimeoutMillis = 1_999
        }
    }
}