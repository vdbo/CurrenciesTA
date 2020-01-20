object Versions {
    const val gradleBuildTools = "3.5.3"
    const val kotlin = "1.3.61"
    const val androidMinSdk = 21
    const val androidTargetSdk = 28
    const val androidXAppCompat = "1.1.0"
    const val androidKtx = "1.1.0"
    const val constraintLayout = "1.1.3"
    const val recyclerView = "1.1.0"
    const val koin = "2.0.1"
    const val retrofit = "2.7.1"
    const val rxJava2 = "2.2.17"
    const val rxAndroid = "2.1.1"
    const val rxKotlin = "2.4.0"
    const val moshiAdapters = "1.9.2"
    const val moshiCodegen = "1.8.0"
    const val viewModel = "2.1.0"
    const val archComponentsCompiler = "2.1.0"
    const val lifecycleExt = "2.1.0"
    const val timber = "4.7.1"
    const val junit = "4.12"
    const val junitAndroid = "1.1.0"
    const val mockk = "1.9"
    const val espresso = "3.1.1"
}

object Dependencies {
    const val gradleBuildTools = "com.android.tools.build:gradle:${Versions.gradleBuildTools}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidXAppCompat}"
    const val androidKtx = "androidx.core:core-ktx:${Versions.androidKtx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val koin = "org.koin:koin-core:${Versions.koin}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val koinTest = "org.koin:koin-test:${Versions.koin}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshiAdapters}"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshiCodegen}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.viewModel}"
    const val archComponentsCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.archComponentsCompiler}"
    const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExt}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val junit = "junit:junit:${Versions.junit}"
    const val junitAndroid = "androidx.test.ext:junit:${Versions.junitAndroid}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}