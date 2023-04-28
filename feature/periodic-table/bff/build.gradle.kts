plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
}

group = "io.thorib.server"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")

    val isDevelopment : Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}


dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.3.0")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.0")
    implementation("io.ktor:ktor-serialization-gson-jvm:2.3.0")
    implementation("io.ktor:ktor-server-call-logging-jvm:2.3.0")
    implementation("io.ktor:ktor-server-cio-jvm:2.3.0")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    implementation(project(":fake-dto"))
    implementation(project(":feature:periodic-table:domain"))

    testImplementation("io.ktor:ktor-server-tests-jvm:2.3.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.7.10")

}
