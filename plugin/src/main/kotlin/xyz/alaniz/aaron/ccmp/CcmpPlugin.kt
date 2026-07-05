package xyz.alaniz.aaron.ccmp

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CcmpPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.create("ccmp", CcmpExtension::class.java, this)

            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
                apply("com.google.devtools.ksp")
                apply("dev.zacsweers.metro")
            }

            val kmpExtension = extensions.getByType(KotlinMultiplatformExtension::class.java)
            kmpExtension.apply {
                androidTarget()
                
                iosArm64()
                iosSimulatorArm64()

                sourceSets.getByName("commonMain").dependencies {
                    // Metro dependency usually required if applying the plugin
                    implementation("dev.zacsweers.metro:runtime:1.1.1")
                }
            }

            val androidExtension = extensions.getByType(LibraryExtension::class.java)
            androidExtension.apply {
                compileSdk = 36
                defaultConfig {
                    minSdk = 24
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }

            tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile::class.java).configureEach {
                it.compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
            }
        }
    }
}
