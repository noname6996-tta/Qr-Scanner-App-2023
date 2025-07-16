// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.9.3")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.56.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.4")
    }
}
plugins {
    id("com.android.application") version "8.9.3" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("com.google.gms.google-services") version "4.4.3" apply false
    id("com.google.dagger.hilt.android") version "2.56.2" apply false
    id("com.google.devtools.ksp") version "2.1.21-2.0.2" apply false
}