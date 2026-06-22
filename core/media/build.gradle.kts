plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose) // Chỉ giữ lại nếu bác vẫn đang để UI trong này
    // Đã xóa: hilt.android, ksp, kotlin.serialization
}

android {
    namespace = "com.example.core.media"
    compileSdk {
        version = release(libs.versions.compilerSdk.get().toInt()) {
            minorApiLevel = libs.versions.minorApiLevel.get().toInt()
        }
    }

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.graphics.shapes) // Cho cái Morphing của bác

     implementation(libs.androidx.media3.exoplayer)
     implementation(libs.androidx.media3.session)
     implementation(libs.androidx.media3.ui.compose)

    implementation(project(":resource"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}