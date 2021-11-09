plugins {
    id("com.android.application")
    id("base-app-plugin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
}
