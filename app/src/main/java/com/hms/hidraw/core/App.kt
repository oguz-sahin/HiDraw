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

package com.hms.hidraw.core

import android.app.Application
import com.hms.hidraw.ui.drawresult.DrawResultFragment
import com.hms.hidraw.util.manager.AnalyticsManager
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.agconnect.crash.AGConnectCrash
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsTools
import com.huawei.hms.videoeditor.ui.api.MediaApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupAnalytics()
        initCrashService()
        setVideoEditorKitApiKey()
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

    private fun setVideoEditorKitApiKey() {
        val apiKey = AGConnectServicesConfig.fromContext(this)
            .getString(DrawResultFragment.AGC_API_KEY)
        MediaApplication.getInstance().setApiKey(apiKey)
    }
}
