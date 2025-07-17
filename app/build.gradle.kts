plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.tta.qrscanner2023application"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.tta.qrscanner2023application"
        minSdk = 23
        targetSdk = 35
        versionCode = 3
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("qr_scan_app.jks")
            keyAlias = "key0"
            keyPassword = "theanh682001"
            storePassword = "theanh682001"
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isDebuggable = true
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.google.firebase:firebase-crashlytics:19.4.4")
    implementation("androidx.activity:activity-ktx:1.10.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.16.0"))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics-ktx")
    // Kotlin
    val nav_version = "2.8.3"
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.1")
    //MLKit
    implementation ("com.google.firebase:firebase-ml-vision:24.1.0")
    implementation ("com.google.firebase:firebase-ml-vision-barcode-model:16.1.2")
    //
    implementation  ("com.google.android.gms:play-services-vision:20.1.3")
    // baloon
    implementation("com.github.skydoves:balloon:1.5.4")
    // permission X
    implementation ("com.guolindev.permissionx:permissionx:1.7.1")

    // zxing
    implementation("com.journeyapps:zxing-android-embedded:4.3.0"){
        isTransitive = false
    }
    implementation ("com.google.zxing:core:3.4.1")

    //
    implementation("androidx.room:room-runtime:2.7.2")
    annotationProcessor("androidx.room:room-compiler:2.7.2")
    implementation("androidx.room:room-ktx:2.7.2")
    // To use Kotlin annotation processing tool (kapt)
    ksp("androidx.room:room-compiler:2.7.2")
    // Hilt
    implementation ("com.google.dagger:hilt-android:2.56.2")
    ksp  ("com.google.dagger:hilt-android-compiler:2.56.2")
    //
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    // data Store
    implementation("androidx.datastore:datastore-preferences-core:1.1.7")
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    //Onboarding
    implementation ("com.github.akshaaatt:Onboarding:1.1.3")
    //
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    //
    implementation("com.blankj:utilcode:1.30.7")
}