plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "br.com.pokemon_center"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.pokemon_center"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)
    implementation(libs.fragment)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.androidx.espresso.contrib)
    implementation(libs.coil)
    implementation(libs.ballon)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.viewpager)
    implementation(libs.photoview)
    ksp(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
