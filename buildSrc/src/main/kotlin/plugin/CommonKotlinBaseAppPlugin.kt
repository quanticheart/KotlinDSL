package plugin

import extentions.java
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import kotlin

class CommonKotlinBaseAppPlugin : Plugin<Project> {
    override fun apply(project: Project) =
        with(project) {
            applyPlugins()
            java()
            dependenciesConfig()
        }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("java-library")
            apply("kotlin")
        }
    }

    private fun Project.dependenciesConfig() {
        dependencies {
            kotlin()
        }
    }
}
