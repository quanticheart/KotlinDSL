plugins {
//    `java-gradle-plugin`
    `kotlin-dsl`
    `lifecycle-base`
    `kotlin-dsl-precompiled-script-plugins`
//    id("org.jetbrains.kotlin.jvm") version "1.3.61"
//    id("com.gradle.plugin-publish") version "0.10.1"
//    id("io.gitlab.arturbosch.detekt") version ("1.18.1")
}

gradlePlugin {
    plugins {
        register("base-app-plugin") {
            id = "base-app-plugin"
            implementationClass = "plugin.CommonBaseAppPlugin"
        }
        register("base-kotlin-plugin") {
            id = "base-kotlin-plugin"
            implementationClass = "plugin.CommonKotlinBaseAppPlugin"
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    google()
    mavenCentral()
}

tasks.withType<PluginUnderTestMetadata>().configureEach {
    pluginClasspath.from(configurations.compileOnly)
}

subprojects {
//    apply {
//        plugin("io.gitlab.arturbosch.detekt")
//    }
//    detekt {
//        buildUponDefaultConfig = true // preconfigure defaults
//        allRules = false // activate all available (even unstable) rules.
//        config =
//            files("../config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
//        baseline =
//            file("../config/detekt/baseline.xml") // a way of suppressing issues before introducing detekt
//
//        reports {
//            xml {
//                enabled = true
//                destination = file("path/to/destination.xml")
//            }
//            html {
//                enabled = true
//                destination = file("path/to/destination.html")
//            }
//            txt {
//                enabled = true
//                destination = file("path/to/destination.txt")
//            }
//            sarif.enabled =
//                true // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
//        }
//    }
}

// Kotlin DSL
//tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
//    // Target version of the generated JVM bytecode. It is used for type resolution.
//    jvmTarget = "1.8"
//}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.3")
    implementation("com.android.tools.build:gradle-api:7.0.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")

//    extentions.implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31")
//    extentions.implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
}
