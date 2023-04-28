plugins {
    `java-library`
    kotlin("jvm")
    kotlin("kapt")
    //kotlin("plugin.serialization")
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-gson:2.3.0") {
        because("I am using it in a different project")
    }
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-okhttp:2.3.0")

    //implementation("io.ktor:ktor-client-cio:2.3.0")
    // https://ktor.io/docs/type-safe-request.html#add_dependencies
    //implementation("io.ktor:ktor-client-resources:2.3.0")
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("io.ktor:ktor-client-logging-jvm:2.3.0")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    implementation("com.google.dagger:dagger:2.16")
    implementation(project(":framework"))
    testImplementation("junit:junit:4.13.1")
    kapt("com.google.dagger:dagger-compiler:2.16")
    kaptTest("com.google.dagger:dagger-compiler:2.16")

    implementation(project(":feature:periodic-table:domain"))
    testImplementation(project(":fake-dto"))

    testImplementation("io.ktor:ktor-client-mock:2.3.0")
    testImplementation(project(":framework-test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("junit:junit:4.13.2")

}