package plugin

import defaultAndroidTestImplementation
import defaultImplementation
import extentions.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.task
import org.gradle.kotlin.dsl.withGroovyBuilder

class CommonBaseAppPlugin : Plugin<Project> {

    override fun apply(project: Project) =
        with(project) {
            applyPlugins()
            androidConfig()
            dependenciesConfig()
            tasks()
        }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("kotlin-android")
//            apply("kotlin-android-extensions")
            apply("kotlin-kapt")
        }

        apply {
            from("$rootDir/config/ktlint.gradle.kts")
//            from("$rootDir/config/detekt/detekt.gradle.kts")
        }
    }

    private fun Project.androidConfig() {
        android.run {
            appVersions(moduleIsLibrary())
            appBuildTypes(moduleIsLibrary())

            withGroovyBuilder {
                "buildFeatures" {
                    setProperty("viewBinding", true)
                }
            }

            withGroovyBuilder {
                "kotlinOptions" {
                    setProperty("jvmTarget", "1.8")
                }
            }

            appCompileOptions()
        }
    }

    private fun Project.dependenciesConfig() {
        dependencies {
            defaultImplementation()
            defaultAndroidTestImplementation()
        }
    }

    private fun Project.tasks() {
        task<Exec>("TEST") {
            commandLine("echo", "test")
        }
    }
}