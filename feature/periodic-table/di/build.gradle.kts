plugins {
    `java-library`
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-logging:2.3.0")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation(project(":feature:periodic-table:repository"))
    implementation(project(":feature:periodic-table:domain"))
    implementation("com.google.dagger:dagger:2.16")

    kapt("com.google.dagger:dagger-compiler:2.16")

    implementation(project(":framework"))
    implementation(project(":feature:periodic-table:repository"))
    implementation(project(":feature:periodic-table:service"))
}