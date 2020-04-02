import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("maven-publish")
}

android {
    configureAndroid()
    defaultConfig {
        minSdkVersion(9)
    }
}

fun KotlinDependencyHandler.coreLibs(platform: String) {
    api(asoftCore("platform", platform))
}

kotlin {
    android {
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
        publishLibraryVariants("release")
    }

    jvm {
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
    }

    js {
        compilations.all {
            kotlinOptions {
                metaInfo = true
                sourceMap = true
                moduleKind = "commonjs"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                coreLibs("metadata")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoftTest("metadata"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                coreLibs("android")
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(asoftTest("android"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                coreLibs("jvm")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(asoftTest("jvm"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                coreLibs("js")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(asoftTest("js"))
            }
        }
    }
}