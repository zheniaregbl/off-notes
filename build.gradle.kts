plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.androidLint) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
}

tasks.register("regenResources") {
    group = "compose"
    description = "Regenerates Compose resources for all modules"

    dependsOn(
        subprojects.flatMap { project ->
            project.tasks.matching {
                it.name == "generateResourceAccessorsForCommonMain" ||
                        it.name == "generateComposeResClass"
            }
        }
    )
}