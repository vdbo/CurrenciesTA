plugins {
    id("kotlin")
    kotlin("kapt")
}

apply(from = "../shared_dependencies.gradle.kts")
apply(from = "../test_dependencies.gradle.kts")

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}