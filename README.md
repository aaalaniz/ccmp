# CCMP Gradle Plugin

An opinionated KMP app platform for building Kotlin Multiplatform applications with Circuit, Compose Multiplatform, and Metro.

## Usage

```kotlin
plugins {
    id("xyz.alaniz.aaron.ccmp") version "0.3.0"
}

ccmp {
    compose = true
    circuit = true
    parcelize = true
    jvm = true

    // Configures Compose Multiplatform resources
    resources {
        // Compose ResourcesExtension configuration
    }
}

android {
    namespace = "xyz.alaniz.aaron.example"
}
```

## Features
- Provides a centralized `ccmp` DSL to toggle features for each module.
- Automatically sets up Kotlin Multiplatform and Android library targets:
  - Android (`androidTarget()`) with `compileSdk = 36` and `minSdk = 24`.
  - iOS (`iosArm64()`, `iosSimulatorArm64()`).
  - Desktop / JVM (`jvm()`) which is opt-in via the `jvm = true` DSL flag.
- Pre-configures known-compatible versions of core dependencies:
  - Kotlin 2.2.20
  - KSP 2.2.20-2.0.4
  - Compose Multiplatform 1.11.0
  - Circuit 0.33.1
  - Metro 1.1.1
- Configures Java 17 for both source/target compatibility and Kotlin `jvmTarget`.
- Using the `resources {}` block automatically enables Compose if it is not already enabled.
