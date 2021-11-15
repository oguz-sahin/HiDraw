package com.huawei.hidraw.core

import android.app.Application
import com.huawei.agconnect.crash.AGConnectCrash
import com.huawei.hidraw.util.manager.AnalyticsManager
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsTools
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Oguz Sahin on 10/26/2021.
 */


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupAnalytics()
        initCrashService()
    }

    private fun setupAnalytics() {
        HiAnalytics.getInstance(this).also {
            AnalyticsManager.analyticsInstance = it
        }.run {
            HiAnalyticsTools.enableLog()
            setAnalyticsEnabled(true)
        }
    }


    private fun initCrashService() {
        AGConnectCrash.getInstance().enableCrashCollection(true)
    }

}