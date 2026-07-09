plugins {
    `version-catalog`
    `maven-publish`
}

group = "xyz.alaniz.aaron.ccmp"
version = file("../version.txt").readText().trim()

catalog {
    versionCatalog {
        version("kotlin", "2.2.0")
        version("ksp", "2.2.0-2.0.2")
        version("compose-multiplatform", "1.11.0")
        version("circuit", "0.33.1")
        version("metro", "1.1.1")

        plugin("kotlin-multiplatform", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
        plugin("kotlin-compose", "org.jetbrains.kotlin.plugin.compose").versionRef("kotlin")
        plugin("ksp", "com.google.devtools.ksp").versionRef("ksp")
        plugin("compose-multiplatform", "org.jetbrains.compose").versionRef("compose-multiplatform")
        plugin("metro", "dev.zacsweers.metro").versionRef("metro")

        library("circuit-foundation", "com.slack.circuit", "circuit-foundation").versionRef("circuit")
        library("circuit-retained", "com.slack.circuit", "circuit-retained").versionRef("circuit")
        library("metro-runtime", "dev.zacsweers.metro", "runtime").versionRef("metro")
        library("metro-compiler", "dev.zacsweers.metro", "compiler").versionRef("metro")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])
        }
    }
}
