plugins {
    kotlin("jvm") version "1.7.10" apply false
    kotlin("kapt") version "1.7.10" apply false
    kotlin("plugin.serialization") version "1.7.10" apply false
    id("io.ktor.plugin") version "2.3.0" apply false

}

group = "io.thorib.dagger"

allprojects {
    val flush by tasks.registering(Delete::class) {
        group = "[platform]"
        delete(buildDir)
        delete("${projectDir}/bin")
    }


    val runBff by tasks.registering {
        group = "[platform]"
        dependsOn(":feature:periodic-table:bff:runFatJar")
    }
}