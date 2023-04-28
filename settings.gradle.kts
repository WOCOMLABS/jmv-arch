rootProject.name = "dagger"

dependencyResolutionManagement{


    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }

}



include(
    ":app" ,
    ":framework" ,
    ":framework-test" ,
    ":fake-dto" ,

    ":feature:periodic-table:domain" ,
    ":feature:periodic-table:repository" ,

    ":feature:periodic-table:service" ,
    ":feature:periodic-table:di" ,
    ":feature:periodic-table:sideeffect" ,

    ":feature:periodic-table:bff" ,

    ":plugin:scaffolding" ,
)
