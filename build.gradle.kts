plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") // 👈 Agrega esta línea

}

android {
    namespace = "mx.edu.utng.mahg.notificaciones01"
    compileSdk = 34

    defaultConfig {
        applicationId = "mx.edu.utng.mahg.notificaciones01"
        minSdk = 26  // Android 8.0 - ¡La mayoría de dispositivos lo tienen!
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Compose - El "pincel" para crear interfaces bonitas
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Core - El "cerebro" de nuestra app
    implementation("androidx.core:core-ktx:1.12.0")
}
