import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.detekt

plugins {
    id("com.github.ben-manes.versions") version "0.39.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1")

//        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1")
//        classpath("com.diffplug.spotless:spotless-plugin-gradle:5.16.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.common.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }
    detekt {
        buildUponDefaultConfig = true // preconfigure defaults
        allRules = false // activate all available (even unstable) rules.
        config =
            rootProject.files("config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
        baseline =
            file("$rootDir/config/detekt/baseline.xml") // a way of suppressing issues before introducing detekt
        autoCorrect = true
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
            sarif.enabled =
                true // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
        }
    }
}

//tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
//    // Target version of the generated JVM bytecode. It is used for type resolution.
//    jvmTarget = "1.8"
//}

tasks {
    register("clean", Delete::class.java) {
        delete(rootProject.buildDir)
    }

    withType<DependencyUpdatesTask> {
        rejectVersionIf {
            candidate.version.isStableVersion().not()
        }
    }
}

fun String.isStableVersion(): Boolean {
    val stableKeyword =
        listOf("RELEASE", "FINAL", "GA").any { toUpperCase(java.util.Locale.ROOT).contains(it) }
    return stableKeyword || Regex("^[0-9,.v-]+(-r)?$").matches(this)
}


val analysisDir = file(projectDir)
val baselineFile = file("$rootDir/config/detekt/baseline.xml")
val configFile = file("$rootDir/config/detekt/detekt.yml")
val statisticsConfigFile = file("$rootDir/config/detekt/statistics.yml")

val kotlinFiles = "**/*.kt"
val kotlinScriptFiles = "**/*.kts"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

val detektFormat by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Formats whole project."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektAll2 by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Runs the whole project at once."
    parallel = true
    buildUponDefaultConfig = true
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektProjectBaseline by tasks.registering(io.gitlab.arturbosch.detekt.DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    buildUponDefaultConfig.set(true)
    ignoreFailures.set(true)
    parallel.set(true)
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
}

