import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization")
    id("androidx.room")
    id("com.google.devtools.ksp")
}

kotlin {

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "TodoApp"
//            isStatic = true
//            // Required when using NativeSQLiteDriver
//            linkerOpts.add("-lsqlite3")
//        }
//    } // sqlite drivers ig
//
//
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            configurations.implementation{// TODO check if is good the skechiest thing ever
                exclude(group = "com.intellij", module = "annotations")
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

            val room_version="2.7.0-alpha11"
            implementation("androidx.room:room-gradle-plugin:$room_version")
            implementation("androidx.room:room-compiler:$room_version")
            implementation("androidx.room:room-runtime:$room_version")
            implementation("androidx.sqlite:sqlite-bundled:2.5.0-alpha01")

            implementation(libs.bundles.ktor)

            implementation(libs.ktor.client.okhttp)

            implementation("io.socket:socket.io-client:2.0.0") // Check for the latest version
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {

    namespace = "org.example.cross_unstabilizer"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.cross_unstabilizer"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

room{
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    debugImplementation(compose.uiTooling)
    val room_version="2.7.0-alpha11"
    ksp("androidx.room:room-compiler:$room_version")
}

compose.desktop {
    application {
        mainClass = "org.example.cross_unstabilizer.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.cross_unstabilizer"
            packageVersion = "1.0.0"
        }
    }
}

