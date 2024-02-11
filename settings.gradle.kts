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

    versionCatalogs {
        create("libs") {
            version("compose", "1.5.4")
            version("compose-activity", "1.8.0")
            version("core-ktx", "1.12.0")
            version("appCompat", "1.6.1")
            version("material3", "1.1.2")

            library("compose-ui", "androidx.compose.ui", "ui").versionRef("compose")
            library("compose-ui-tooling", "androidx.compose.ui", "ui-tooling").versionRef("compose")
            library(
                "compose-ui-tooling-preview",
                "androidx.compose.ui",
                "ui-tooling-preview"
            ).versionRef("compose")
            library(
                "compose-ui-graphics",
                "androidx.compose.ui",
                "ui-graphics"
            ).versionRef("compose")
            library(
                "compose-ui-test",
                "androidx.compose.ui",
                "ui-test-junit4"
            ).versionRef("compose")
            library(
                "compose-ui-test-manifest",
                "androidx.compose.ui",
                "ui-test-manifest"
            ).versionRef("compose")
            library(
                "compose-activity",
                "androidx.activity",
                "activity-compose"
            ).versionRef("compose-activity")
            bundle(
                "compose", listOf(
                    "compose-ui",
                    "compose-ui-tooling",
                    "compose-ui-tooling-preview",
                    "compose-ui-graphics",
                    "compose-ui-test",
                    "compose-ui-test-manifest",
                    "compose-activity"
                )
            )

            library("core-ktx", "androidx.core", "core-ktx").versionRef("core-ktx")

            library("appcompat", "androidx.appcompat", "appcompat").versionRef("appCompat")

            library("material3", "androidx.compose.material3", "material3").versionRef("material3")

        }
    }
}

rootProject.name = "CurrencyExchanger"
include(":app")
 