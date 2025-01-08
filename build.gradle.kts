import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    // Platforms
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    // Serialization
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    compilerOptions.freeCompilerArgs.apply {
        add("-Xconsistent-data-class-copy-visibility")
    }

    jvm()
    js {
        browser()

        binaries.library()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()

        binaries.library()
    }
    androidTarget("android") {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    val nativeLibraryBaseName = "gigachat_entities"
    linuxX64 {
        binaries {
            sharedLib {
                baseName = nativeLibraryBaseName
            }
        }
    }
    linuxArm64 {
        binaries {
            sharedLib {
                baseName = nativeLibraryBaseName
            }
        }
    }
    mingwX64 {
        binaries {
            sharedLib {
                baseName = nativeLibraryBaseName
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                // Kotlin extensions
                // DateTime
                api(libs.kotlinx.datetime)
            }
        }
    }
}

android {
    namespace = "gc"
    compileSdk = libs.versions.android.compileSDK.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minimumSDK.get().toInt()
    }
}
