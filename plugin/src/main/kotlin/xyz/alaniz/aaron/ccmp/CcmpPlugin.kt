package xyz.alaniz.aaron.ccmp

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

class CcmpPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.create("ccmp", CcmpExtension::class.java, this)

            val expectedKotlinVersion = "2.2.0"
            val expectedComposeVersion = "1.11.0"

            plugins.withType(KotlinBasePluginWrapper::class.java) { plugin ->
                if (plugin.pluginVersion != expectedKotlinVersion) {
                    logger.warn("CCMP Warning: Expected Kotlin version $expectedKotlinVersion but found ${plugin.pluginVersion}.")
                }
            }

            pluginManager.withPlugin("org.jetbrains.compose") {
                val composeVersion = plugins.getPlugin("org.jetbrains.compose").javaClass.protectionDomain.codeSource.location.toString()
                if (!composeVersion.contains(expectedComposeVersion)) {
                    logger.warn("CCMP Warning: Applied Compose Multiplatform plugin version does not match expected version $expectedComposeVersion.")
                }
            }

            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
                apply("com.google.devtools.ksp")
                apply("dev.zacsweers.metro")
            }

            val kmpExtension = extensions.getByType(KotlinMultiplatformExtension::class.java)
            kmpExtension.apply {
                androidTarget()
                
                iosX64()
                iosArm64()
                iosSimulatorArm64()

                // Desktop / JVM target if applicable
                jvm()
                
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
