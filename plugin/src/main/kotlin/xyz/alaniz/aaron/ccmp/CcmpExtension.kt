package xyz.alaniz.aaron.ccmp

import org.gradle.api.Action
import org.gradle.api.Project
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.resources.ResourcesExtension
import javax.inject.Inject

open class CcmpExtension @Inject constructor(private val project: Project) {
    var compose: Boolean = false
        set(value) {
            field = value
            if (value) {
                project.pluginManager.apply("org.jetbrains.compose")
                project.pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            }
        }

    var circuit: Boolean = false
        set(value) {
            field = value
            if (value) {
                // Circuit typically requires KSP which is applied in the base plugin
                project.dependencies.add("commonMainImplementation", "com.slack.circuit:circuit-foundation:0.33.1")
                project.dependencies.add("commonMainApi", "com.slack.circuit:circuit-codegen-annotations:0.33.1")

                val kspExtension = project.extensions.findByType(com.google.devtools.ksp.gradle.KspExtension::class.java)
                kspExtension?.arg("circuit.codegen.mode", "metro")

                val kspConfigurations = listOf(
                    "kspCommonMainMetadata",
                    "kspAndroid",
                    "kspIosArm64",
                    "kspIosSimulatorArm64",
                    "kspIosX64"
                )
                kspConfigurations.forEach { configuration ->
                    project.dependencies.add(configuration, "com.slack.circuit:circuit-codegen:0.33.1")
                }
            }
        }

    var parcelize: Boolean = false
        set(value) {
            field = value
            if (value) {
                project.pluginManager.apply("org.jetbrains.kotlin.plugin.parcelize")
            }
        }

    var jvm: Boolean = false
        set(value) {
            field = value
            if (value) {
                val kmpExtension = project.extensions.getByType(org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension::class.java)
                kmpExtension.jvm()
            }
        }


    fun resources(action: Action<ResourcesExtension>) {
        if (!compose) {
            compose = true
        }
        val composeExt = project.extensions.getByType(ComposeExtension::class.java)
        val resourcesExt = composeExt.extensions.getByType(ResourcesExtension::class.java)
        action.execute(resourcesExt)
    }
}
