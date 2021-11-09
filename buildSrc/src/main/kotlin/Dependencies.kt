import extentions.androidTestImplementation
import extentions.implementation
import extentions.testImplementation
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope

object Dependencies {
    /**
     * Kotlin std lib
     */
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    /**
     * UI
     */
    private const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private const val material = "com.google.android.material:material:1.4.0"
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val navigationF =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationUI}"
    private const val navigationUI =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationUI}"

    /**
     * Tests
     */
    private const val junit = "junit:junit:${Versions.junit}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    /**
     * App libraries
     */
    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(material)
        add(constraintLayout)
        add(navigationF)
        add(navigationUI)
    }

    /**
     * Android test libraries
     */
    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    /**
     * Test libraries
     */
    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}

/**
 * Default implementation
 *
 */
fun DependencyHandler.kotlin() {
    add("implementation", Dependencies.kotlinStdLib)
}

fun DependencyHandlerScope.defaultImplementation() {
    implementation(Dependencies.appLibraries)
}

fun DependencyHandlerScope.defaultAndroidTestImplementation() {
    androidTestImplementation(Dependencies.androidTestLibraries)
    testImplementation(Dependencies.testLibraries)
}

