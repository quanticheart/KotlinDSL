package plugin

import defaultAndroidTestImplementation
import defaultImplementation
import extentions.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withGroovyBuilder

class CommonBaseAppPlugin : Plugin<Project> {

    override fun apply(project: Project) =
        with(project) {
            applyPlugins()
            androidConfig()
            dependenciesConfig()
        }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("kotlin-android")
            apply("kotlin-android-extensions")
            apply("kotlin-kapt")
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
}
