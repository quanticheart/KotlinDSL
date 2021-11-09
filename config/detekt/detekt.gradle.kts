//val detekt by configurations.creating
//
//dependencies {
//    detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.18.1")
//}

//apply(plugin = "io.gitlab.arturbosch.detekt")

//
//val detektTask = tasks.register<JavaExec>("detekt") {
//    main = "io.gitlab.arturbosch.detekt.cli.Main"
//    classpath = detekt
//
//    val input = projectDir
//    val config = "../config/detekt/detekt.yml"
//    val exclude = ".*/build/.*,.*/resources/.*"
//    val params = listOf("-i", input, "-c", config, "-ex", exclude)
//
//    args(params)
//}
//
//tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
//    // Target version of the generated JVM bytecode. It is used for type resolution.
//    this.jvmTarget = "1.8"
//}
//
//tasks.register<io.gitlab.arturbosch.detekt.Detekt>("myDetekt") {
//    description = "Runs a custom detekt build."
//    setSource(files("src/main/kotlin", "src/test/kotlin"))
//    config.setFrom(files("$rootDir/config.yml"))
//    debug = true
//    reports {
//        xml {
//            destination = file("build/reports/mydetekt.xml")
//        }
//        html.destination = file("build/reports/mydetekt.html")
//    }
//    include("**/*.kt")
//    include("**/*.kts")
//    exclude("resources/")
//    exclude("build/")
//}


// Remove this block if you don't want to run detekt on every build
//tasks.check {
//    dependsOn(detektTask)
//}