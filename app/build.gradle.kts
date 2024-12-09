plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Add the kapt plugin here
    id("kotlin-kapt")
}

android {
    namespace = "com.example.videoapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.videoapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        buildConfig=true
    }

    buildTypes {
        debug {
            debug { true }
            buildConfigField("String", "MOVIEDB_API_ACCESS_KEY", "\"${findProperty("MOVIEDB_API_ACCESS_KEY")}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ViewModel
    implementation("androidx.compose.runtime:runtime:1.6.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    //Material Icons
    implementation("androidx.compose.material:material-icons-extended:1.5.0")

    //Refresh Page
    //implementation("androidx.compose.foundation:foundation:1.3.0")


    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // RxJava 2
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")

    // Retrofit Adapter for RxJava2
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    // Coil for Async Image
    implementation("io.coil-kt:coil-compose:2.4.0")

    // LiveData as State
    implementation("androidx.compose.runtime:runtime-livedata:1.0.0-beta01")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    //annotationProcessor("androidx.room:room-compiler:2.6.1")
    //implementation("androidx.room:room-rxjava3:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Dagger
    implementation("com.google.dagger:dagger:2.53")
    kapt("com.google.dagger:dagger-compiler:2.53")
}
