import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
    id("com.huawei.agconnect")
    id("kotlin-android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version Versions.kt_lint
    id("io.gitlab.arturbosch.detekt") version Versions.detekt
    id("org.sonarqube") version Versions.sonarqube
}

sonarqube {
    properties {
        property("sonar.projectKey", getProperty("SONAR_PROJECT_KEY"))
        property("sonar.projectName", getProperty("SONAR_PROJECT_NAME"))
        property("sonar.login", getProperty("SONAR_LOGIN"))
        property("sonar.host.url", getProperty("SONAR_HOST_URL"))
        property("sonar.projectVersion", Config.version_name)
    }
}

android {
    compileSdk = Config.compile_sdk_version

    defaultConfig {
        applicationId = Config.application_id
        minSdk = Config.min_sdk_version
        targetSdk = Config.target_sdk_version
        versionCode = Config.version_code
        versionName = Config.version_name
        multiDexEnabled = true
        buildConfigField("String", "BASE_URL", "\"http://159.138.96.192:8081/api/\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val storeFilePath = getProperty("STORE_FILE")
            storeFile = file(storeFilePath)
            storePassword = getProperty("STORE_PASSWORD")
            keyAlias = getProperty("KEY_ALIAS")
            keyPassword = getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {

        release {
            signingConfig = signingConfigs.findByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            signingConfig = signingConfigs.findByName("release")
            isDebuggable = true
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("main") {
            java {
                srcDirs("build/generated/source/navigation-args")
            }
        }
    }
}

fun getProperty(propertyName: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyName)
}

dependencies {
    coreLibraryDesugaring(Libs.desugaring)
    implementation(Libs.core)
    implementation(Libs.appcompat)
    implementation(Libs.material_design)
    implementation(Libs.constraint_layout)
    implementation(Libs.legacy_support)
    testImplementation(Libs.jUnit)
    androidTestImplementation(Libs.jUnit_test)
    androidTestImplementation(Libs.espresso)
    implementation(Libs.activity_ktx)
    implementation(Libs.fragment_ktx)
    implementation(Libs.lifecycle_ext)
    implementation(Libs.lifecycleRuntime)
    implementation(Libs.lifecycle_common)
    implementation(Libs.lifecycle_view_model)
    implementation(Libs.retrofit)
    implementation(Libs.gson_converter)
    implementation(Libs.okhttp_interceptor)
    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui)
    implementation(Libs.glide)
    annotationProcessor(Libs.glide_compiler)
    implementation(Libs.hilt)
    kapt(Libs.hilt_compiler)
    implementation(Libs.coroutines_core)
    implementation(Libs.coroutines_android)
    implementation(Libs.livedata)
    implementation(Libs.lottie)
    implementation(Libs.sdp)
    implementation(Libs.ag_connect)
    implementation(Libs.analytic_kit)
    implementation(Libs.crash_service)
    implementation(Libs.account_kit)
    implementation(Libs.push_kit)
    implementation(Libs.hbRecorder)
    implementation(Libs.video_editor_kit)
}
