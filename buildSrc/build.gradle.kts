plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
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

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.2")
//    extentions.implementation("com.android.tools.build:gradle-api:4.2.2")
//    extentions.implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31")
//    extentions.implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
}