package xyz.alaniz.aaron.ccmp

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class CcmpPluginTest {

    @TempDir
    lateinit var testProjectDir: File

    private lateinit var buildFile: File

    @BeforeEach
    fun setup() {
        buildFile = File(testProjectDir, "build.gradle.kts")
        // Need to set up a minimal valid android environment to avoid errors
        File(testProjectDir, "local.properties").writeText("sdk.dir=/Users/aaron/Library/Android/sdk\n")
        File(testProjectDir, "gradle.properties").writeText("android.useAndroidX=true\n")
        File(testProjectDir, "src/main/AndroidManifest.xml").apply {
            parentFile.mkdirs()
            writeText("""
                <?xml version="1.0" encoding="utf-8"?>
                <manifest xmlns:android="http://schemas.android.com/apk/res/android"
                    package="com.example.test">
                </manifest>
            """.trimIndent())
        }
    }

    @Test
    fun `plugin applies successfully and configures extension`() {
        buildFile.writeText("""
            plugins {
                id("xyz.alaniz.aaron.ccmp")
            }

            ccmp {
                compose = true
                circuit = true
                parcelize = true
            }

            android {
                namespace = "com.example.test"
            }
        """.trimIndent())

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments("tasks")
            .withPluginClasspath()
            .build()

        assertTrue(result.output.contains("BUILD SUCCESSFUL"))
    }
}
