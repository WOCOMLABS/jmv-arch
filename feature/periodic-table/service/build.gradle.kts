plugins {
    `java-library`
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.google.dagger:dagger:2.16")
    kapt("com.google.dagger:dagger-compiler:2.16")

    implementation(project(":framework"))
    implementation(project(":feature:periodic-table:domain"))
    implementation(project(":feature:periodic-table:repository"))

    kaptTest("com.google.dagger:dagger-compiler:2.16")
    testImplementation(project(":framework-test"))
    testImplementation(project(":fake-dto"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("junit:junit:4.13.2")
}