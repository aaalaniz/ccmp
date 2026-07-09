# CCMP Gradle Plugin

An opinionated KMP app platform for building Kotlin Multiplatform applications with Circuit, Compose Multiplatform, and Metro.

## Usage

```kotlin
plugins {
    id("xyz.alaniz.aaron.ccmp") version "0.1.0"
}

ccmp {
    compose = true
    circuit = true
    parcelize = true
}

android {
    namespace = "xyz.alaniz.aaron.example"
}
```

## Features
- Provides a centralized `ccmp` DSL to toggle features for each module.
- Automatically sets up Kotlin Multiplatform and Android library targets.
- Pre-configures known-compatible versions of core dependencies:
  - Kotlin 2.3.21
  - KSP 2.3.21-2.0.4
  - Compose Multiplatform 1.11.0
  - Circuit 0.33.1
  - Metro 1.1.1
- Configures Android and iOS targets out of the box.
