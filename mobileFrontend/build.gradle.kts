plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0" apply false

    val roomVersion="2.7.0-alpha11"
    id("androidx.room") version roomVersion apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.25" apply false // Use the latest version

}