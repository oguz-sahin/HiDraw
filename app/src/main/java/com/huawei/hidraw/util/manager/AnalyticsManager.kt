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