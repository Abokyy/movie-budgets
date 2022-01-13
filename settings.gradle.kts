pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Movie_Budgets"
include(":androidApp")
include(":core")
include(":features")
include(":data")
include(":domain")
