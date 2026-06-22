pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Music"
include(":app")
include(":domain")
include(":data")
include(":core:ui")
include(":core:media")
include(":feature:splash")
include(":feature:home")
include(":resource")
include(":feature:library")
include(":core:utils")
include(":feature:albumdetail")
include(":feature:playing")
