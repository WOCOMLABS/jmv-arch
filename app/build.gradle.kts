import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    application
}

application {
    mainClass.set("io.thorib.cli.MainKt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation(project(":framework"))
    implementation(project(":feature:periodic-table:domain"))
    implementation(project(":feature:periodic-table:repository"))
    implementation(project(":feature:periodic-table:service"))
    implementation(project(":feature:periodic-table:sideeffect"))
    implementation(project(":feature:periodic-table:di"))

    implementation("com.google.dagger:dagger:2.16")
    kapt("com.google.dagger:dagger-compiler:2.16")

}



tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
