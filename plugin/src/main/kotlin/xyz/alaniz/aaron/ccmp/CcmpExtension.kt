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
            }
        }

    var parcelize: Boolean = false
        set(value) {
            field = value
            if (value) {
                project.pluginManager.apply("org.jetbrains.kotlin.plugin.parcelize")
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
