package extentions

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

const val DEBUG = "debug"
const val RELEASE = "release"

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun Project.debugVariant(action: () -> Unit) {
    when (this) {
        is AppExtension -> applicationVariants
            .matching { it.buildType.name == DEBUG }
            .all {
                action()
            }
        is LibraryExtension -> libraryVariants
            .matching { it.buildType.name == DEBUG }
            .all {
                action()
            }
        else -> action()
    }
}

fun Project.releaseVariant(action: () -> Unit) {
    when (this) {
        is AppExtension -> applicationVariants
            .matching { it.buildType.name == RELEASE }
            .all {
                action()
            }
        is LibraryExtension -> libraryVariants
            .matching { it.buildType.name == RELEASE }
            .all {
                action()
            }
        else -> action()
    }
}
