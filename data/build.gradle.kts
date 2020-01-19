plugins {
    id("kotlin")
    kotlin("kapt")
}

apply(from = "../shared_dependencies.gradle.kts")
apply(from = "../test_dependencies.gradle.kts")

configure<JavaPluginConvention> {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api(project(":domain"))

    api(Dependencies.retrofit)
    api(Dependencies.retrofitMoshi)
    api(Dependencies.retrofitRxJava)
    api(Dependencies.moshiAdapters)
    kapt(Dependencies.moshiCodegen)
}
