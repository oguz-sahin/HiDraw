/**
 * Created by Oguz Sahin on 12/20/2021.
 */
object Libs {
    const val hbRecorder = "com.github.HBiSoft:HBRecorder:${Versions.hbRecorder}"
    const val desugaring = "com.android.tools:desugar_jdk_libs:${Versions.desugaring}"
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material_design = "com.google.android.material:material:${Versions.material_design}"
    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val legacy_support = "androidx.legacy:legacy-support-v4:${Versions.legacy_support}"
    const val jUnit = "junit:junit:${Versions.junit}"
    const val jUnit_test = "androidx.test.ext:junit:${Versions.junit_test}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
    const val lifecycle_ext = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_ext}"
    const val lifecycle_common =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle_common}"
    const val lifecycle_view_model =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_view_model}"
    const val lifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_runtime}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_interceptor}"
    const val navigation_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_android}"
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val sdp = "com.intuit.sdp:sdp-android:${Versions.sdp}"
    const val ag_connect = "com.huawei.agconnect:agconnect-core:${Versions.ag_connect}"
    const val analytic_kit = "com.huawei.hms:hianalytics:${Versions.analytic_kit}"
    const val crash_service = "com.huawei.agconnect:agconnect-crash:${Versions.crash_service}"
    const val account_kit = "com.huawei.hms:hwid:${Versions.account_kit}"
    const val push_kit = "com.huawei.hms:push:${Versions.push_kit}"

}

object ClassPaths {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav}"
    const val ag_connect = "com.huawei.agconnect:agcp:${Versions.ag_connect}"
}
