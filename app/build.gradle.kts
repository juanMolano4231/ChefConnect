plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.chefconnect"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.chefconnect"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {

    // Compose
    implementation("androidx.compose.ui:ui:1.7.0")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    implementation("androidx.datastore:datastore-preferences:1.1.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.0")
}