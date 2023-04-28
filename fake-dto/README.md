this module contains all the mock data as json files 


should not publish to maven
should not depend on other gradle modules

```kotlin
plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.code.gson:gson:2.10.1")
}
```