plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

apply(from = "../shared_dependencies.gradle.kts")
apply(from = "../test_dependencies.gradle.kts")

android {
    compileSdkVersion(Versions.androidTargetSdk)
    defaultConfig {
        applicationId = "com.vbta.currenciesta"
        minSdkVersion(Versions.androidMinSdk)
        targetSdkVersion(Versions.androidTargetSdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    java {
        compileOptions {
            targetCompatibility = JavaVersion.VERSION_1_8
            sourceCompatibility = JavaVersion.VERSION_1_8
        }
    }
    sourceSets {
        getByName("main")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "androidx") {
            if (!requested.name.startsWith("multidex")) {
                useVersion("${Versions.androidTargetSdk}.+")
            }
        }
    }
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api(project(":domain"))
    api(project(":data"))

    api(Dependencies.appCompat)
    api(Dependencies.androidKtx)
    api(Dependencies.constraintLayout)
    api(Dependencies.recyclerView)
    api(Dependencies.glide)
    api(Dependencies.koinViewModel)
    api(Dependencies.viewModel)
    api(Dependencies.lifecycleExt)

    kapt(Dependencies.archComponentsCompiler)

    androidTestImplementation(Dependencies.junitAndroid)
    androidTestImplementation(Dependencies.espresso)
}
