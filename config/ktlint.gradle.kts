import extentions.*

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:0.42.1") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}


val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

taskJavaRegister("ktlint") {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    group = "verification"
    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("src/**/*.kt")
}

taskJavaRegister("ktlintFormat") {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    group = "formatting"
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}

tasks.getByName("check").dependsOn(tasks.getByName("ktlint"))

debugVariant {
    taskBuildDependsOn("ktlintFormat")
}

releaseVariant {
    taskBuildDependsOn("ktlint")
}

taskRegisterCommandLine(
    "KtlintApplyToIDEA",
    "ktlint applyToIDEA",
    "formatting",
    "To change the code style config files for all IDEA projects."
)

taskRegisterCommandLine(
    "ktlintAndroidApplyToIDEA",
    "ktlint --android applyToIDEA",
    "formatting",
    "To change the code style Android config files for all IDEA projects."
)
