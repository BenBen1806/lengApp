

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.lengapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lengapp"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

//dependencies {
//    implementation("com.google.android.material:material:1.9.0")
//    implementation("androidx.recyclerview:recyclerview:1.3.1")
//    implementation("com.google.firebase:firebase-database:20.3.3") // Optional for cloud data
//    //implementation("com.android.support:media:28.0.0") // For audio playback
//}

