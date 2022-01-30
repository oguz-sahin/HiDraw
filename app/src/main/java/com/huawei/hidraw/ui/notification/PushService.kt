package com.huawei.hidraw.ui.notification

import android.os.Bundle
import android.util.Log
import com.huawei.hidraw.data.datasource.local.PrefDataSource
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 20.01.2022.
 */
@AndroidEntryPoint
class PushService : HmsMessageService() {

    @Inject
    lateinit var prefDataSource: PrefDataSource

    companion object {
        const val CHANNEL_ID = "drawNotificationChannelId"
    }

    override fun onNewToken(token: String?, bundle: Bundle?) {
        Log.d("pushToken", "have received refresh token:$token")

        token?.let {
            prefDataSource.pushToken = token
        }


        //UpdateUserToken

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        //   showNotification(drawId, title, content)
    }

    /*
    private fun showNotification(drawId: Long, title: String, content: String) {
        val pendingIntent = getPendingIntent(drawId)
        createNotificationChannel()
        val notificationBuilder = getNotificationBuilder(title, content, pendingIntent)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1001, notificationBuilder.build())

    }

    private fun getPendingIntent(drawId: Long): PendingIntent {
        return NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.drawDetailFragment)
            .setArguments(bundleOf("drawId" to drawId))
            .createPendingIntent()
    }

    private fun getNotificationBuilder(
        title: String,
        content: String,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_common_surprise)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "drawNotificationChannel"
            val descriptionText = "Tis is for Draw result notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                setShowBadge(true)
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

     */
}
