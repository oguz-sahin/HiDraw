package com.huawei.hidraw.ui.notification

import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Oguz Sahin on 20.01.2022.
 */
@AndroidEntryPoint
class PushService : HmsMessageService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
    }

}
