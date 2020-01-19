plugins {
    id("kotlin")
    kotlin("kapt")
}

apply(from = "../shared_dependencies.gradle.kts")
apply(from = "../test_dependencies.gradle.kts")

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api(project(":domain"))

    api(Dependencies.retrofit)
    api(Dependencies.retrofitMoshi)
    api(Dependencies.retrofitRxJava)
    kapt(Dependencies.moshiCodegen)
}
