plugins {
    kotlin("jvm")
    `maven-publish`
}

repositories {
    mavenCentral()
}

group = "io.thorib.plugin"
version = "1.0.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.squareup.okio:okio:3.3.0")
}



//publishing {
//    publications {
//        create<MavenPublication>("plugin") {
//            from(components["kotlin"])
//        }
//    }
//    repositories {
//        mavenLocal()
//    }
//}
