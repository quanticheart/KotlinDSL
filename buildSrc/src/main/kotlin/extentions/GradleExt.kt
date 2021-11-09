package extentions

import App
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.kotlin.dsl.configure

val Project.android: BaseExtension
    get() = extensions.findByName("android") as? BaseExtension
        ?: error("Not an Android module: $name")

fun Project.moduleIsLibrary(): Boolean {
    return pluginManager.findPlugin("com.android.library")?.let {
        true
    } ?: pluginManager.findPlugin("java-library")?.let {
        true
    } ?: false
}

fun Project.moduleIsJavaLibrary(): Boolean {
    return pluginManager.findPlugin("java-library")?.let {
        true
    } ?: false
}

/**
 * App versions
 *
 * @param moduleIsLibrary
 */
fun BaseExtension.appVersions(moduleIsLibrary: Boolean) {
    compileSdkVersion(App.compileSdk)

    defaultConfig {
        if (!moduleIsLibrary) {
            applicationId = App.id
        }
        minSdk = App.minSdk
        targetSdk = App.targetSdk
        versionCode = App.versionCode
        versionName = App.versionName

        testInstrumentationRunner = App.androidTestInstrumentation
    }
}

/**
 * App build types
 *
 * @param androidIsLibrary
 */
fun BaseExtension.appBuildTypes(androidIsLibrary: Boolean) {
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true

            if (!androidIsLibrary)
                applicationIdSuffix = ".debug"
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(App.proguardAndroidOptimize),
                App.proguardConsumerRules
            )
        }

        /**
         * The `initWith` property allows you to copy configurations from other build types,
         * then configure only the settings you want to change. This one copies the debug build
         * type, and then changes the manifest placeholder and application ID.
         */
        create("staging") {
            initWith(getByName("debug"))
            manifestPlaceholders["hostName"] = "internal.example.com"

            if (!androidIsLibrary)
                applicationIdSuffix = ".debugStaging"
        }
    }
}

/**
 * Java version
 */
private val javaVersion = JavaVersion.VERSION_1_8

/**
 * App compile options
 *
 */
fun BaseExtension.appCompileOptions() {
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}

/**
 * Java
 *
 */
fun Project.java() {
    configure<JavaPluginConvention> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}


