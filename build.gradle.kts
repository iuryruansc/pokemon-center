plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.gradle.ktlint) apply false
    alias(libs.plugins.detekt) apply false
}