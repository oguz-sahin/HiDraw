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
}

android {
    compileSdk = Config.compile_sdk_version

    defaultConfig {
        applicationId = Config.application_id
        minSdk = Config.min_sdk_version
        targetSdk = Config.target_sdk_version
        versionCode = Config.version_code
        versionName = Config.version_name
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
    return project.property(propertyName) as String
}

dependencies {

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
}
