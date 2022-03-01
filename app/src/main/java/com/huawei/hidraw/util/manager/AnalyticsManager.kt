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

package com.huawei.hidraw.util.manager

import android.os.Bundle
import com.huawei.hms.analytics.HiAnalyticsInstance

/**
 * Created by Oguz Sahin on 10/27/2021.
 */
object AnalyticsManager {

    var analyticsInstance: HiAnalyticsInstance? = null

    fun sendEvent(eventName: String, bundleBlock: (Bundle.() -> Unit)? = null) {
        val bundle = bundleBlock?.let { Bundle().apply(it) }
        analyticsInstance?.onEvent(eventName, bundle)
    }
}
