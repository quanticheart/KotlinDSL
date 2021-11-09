package extentions

import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.task
import org.gradle.kotlin.dsl.withType

fun Project.taskBuildDependsOn(vararg taskName: String) {
    taskName.forEach {
        tasks.withType<JavaCompile> {
            dependsOn(tasks.getByName(it))
        }
    }
}

fun Project.taskRegisterCommandLine(
    taskName: String,
    command: String,
    groupTask: String? = null,
    descriptionTask: String? = null
) {
    task<Exec>(taskName) {
        groupTask?.let { group = it }
        descriptionTask?.let { description = it }
        commandLine("echo", command)
    }
}

fun Project.taskRegister(
    taskName: String,
    groupTask: String? = null,
    descriptionTask: String? = null,
    exec: Exec.() -> Unit
) {
    task<Exec>(taskName) {
        groupTask?.let { group = it }
        descriptionTask?.let { description = it }
        exec()
    }
}

fun Project.taskJavaRegister(
    taskName: String,
    groupTask: String? = null,
    descriptionTask: String? = null,
    execJava: JavaExec.() -> Unit
) {
    tasks.register<JavaExec>(taskName) {
        groupTask?.let { group = it }
        descriptionTask?.let { description = it }
        execJava()
    }
}
