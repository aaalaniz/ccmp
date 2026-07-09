plugins {
    kotlin("jvm") version "2.3.21"
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "xyz.alaniz.aaron.ccmp"
version = file("../version.txt").readText().trim()

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.21")
    implementation("com.android.tools.build:gradle:8.13.2")
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.4")
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.11.0")
    implementation("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:2.3.21")
    implementation("dev.zacsweers.metro:gradle-plugin:1.1.1")
    implementation("org.jetbrains.kotlin:kotlin-parcelize-compiler:2.3.21") // For parcelize if needed in classpath
}

gradlePlugin {
    website.set("https://github.com/aaalaniz/ccmp")
    vcsUrl.set("https://github.com/aaalaniz/ccmp")
    plugins {
        create("ccmp") {
            id = "xyz.alaniz.aaron.ccmp"
            implementationClass = "xyz.alaniz.aaron.ccmp.CcmpPlugin"
            displayName = "CCMP Gradle Plugin"
            description = "An opinionated KMP app platform for Circuit, Compose Multiplatform, and Metro."
            tags.set(listOf("kmp", "compose", "circuit", "metro"))
        }
    }
}

dependencies {
    implementation(gradleApi())
    testImplementation("org.jetbrains.kotlin:kotlin-test:2.2.20")
    testImplementation(gradleTestKit())
}

tasks.withType<Test> {
    useJUnitPlatform()
}
