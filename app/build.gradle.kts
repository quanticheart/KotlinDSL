plugins {
    id("com.android.application")
    id("base-app-plugin")
    id("kotlin-android")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
}

