plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // 🔥 必须添加：Compose Compiler（Kotlin 2.0+ 强制要求）
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20"

    id("com.google.dagger.hilt.android") version "2.57.2"
    kotlin("kapt")  // 保留用于 Hilt；后期可切换 KSP

    // 🔥 新增：KSP 插件（用于 Room，匹配 Kotlin 2.0.20）
    id("com.google.devtools.ksp") version "2.0.20-1.0.25"
}

android {
    namespace = "com.example.wellnessassistant"
    compileSdk = 35  // 更新到 35 以支持新依赖 API

    defaultConfig {
        applicationId = "com.example.wellnessassistant"
        minSdk = 26  // 提高到 26 以匹配 Health Connect 要求
        targetSdk = 34  // 回滚到 34 以最小化运行时变化（未来可升到 35）
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    // Kotlin 2.0+ 新要求：用 compose plugin 控制，不需要再写 jvmTarget
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        // 🔥 必须改成和 kotlin 版本一致：解决 “Compose Compiler plugin required”
        kotlinCompilerExtensionVersion = "2.0.20"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")  // 更新到 1.15.0，支持 SDK 35
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")  // 更新到最新稳定版
    implementation("androidx.activity:activity-compose:1.9.2")  // 更新到最新稳定版

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2025.11.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // 添加 Compose 与 Lifecycle 集成（问题中提到 2.9.4，由 BOM 管理）
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
    implementation("androidx.lifecycle:lifecycle-runtime-compose")

    implementation("androidx.navigation:navigation-compose:2.8.3")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.57.2")
    kapt("com.google.dagger:hilt-compiler:2.57.2")  // 暂留 kapt；后期切换 KSP

    // Room（切换到 KSP）
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")  // 🔥 从 kapt 改为 ksp

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Health Connect
    implementation("androidx.health.connect:connect-client:1.1.0")  // 升级到稳定版 1.1.0（2025.10.08）

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.11.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}