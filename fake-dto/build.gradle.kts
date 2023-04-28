plugins {
    `java-library`
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")

    implementation("com.google.dagger:dagger:2.16")
    kapt("com.google.dagger:dagger-compiler:2.16")

    implementation("com.squareup.okio:okio:3.3.0")
    implementation(project(":framework"))


}