package com.example.pushnotificationsdk

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.pushnotificationsdk.Models.NotificationData
import com.example.pushnotificationsdk.Models.RemoteMessageData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


abstract class PushMessengingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val remoteMessageData = RemoteMessageData()

        remoteMessageData.messageType = remoteMessage.messageType
        remoteMessageData.collapseKey = remoteMessage.collapseKey
        remoteMessageData.from = remoteMessage.from
        remoteMessageData.messageId = remoteMessage.messageId
        remoteMessageData.originalPriority = remoteMessage.originalPriority
        remoteMessageData.priority = remoteMessage.priority
        remoteMessageData.sentTime = remoteMessage.sentTime
        remoteMessageData.to = remoteMessage.to

        if (remoteMessage.data.isNotEmpty()) {

            remoteMessageData.data = remoteMessage.data

        }

        // Check if message contains a notification payload.
        var notification = remoteMessage.notification
        if (notification != null) {

            remoteMessageData.tag = notification.tag
            remoteMessageData.bodyLocalizationKey = notification.bodyLocalizationKey
            remoteMessageData.bodyLocalizationArgs = notification.bodyLocalizationArgs
            remoteMessageData.title = notification.title
            remoteMessageData.body = notification.body
            remoteMessageData.clickAction = notification.clickAction
            remoteMessageData.color = notification.color
            remoteMessageData.icon = notification.icon
            remoteMessageData.link = notification.link
            remoteMessageData.sound = notification.sound
            remoteMessageData.titleLocalizationKey = notification.titleLocalizationKey
            remoteMessageData.titleLocalizationArgs = notification.titleLocalizationArgs
        }


        // show notification to user if handleNotificationPayload returns a value
        handleNotificationData(applicationContext, remoteMessageData)?.let {
            sendNotification(it)
        }

    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Notification.sendRegistrationToServer(s)

    }

    abstract fun handleNotificationData(context: Context, remoteMessageData : RemoteMessageData) : NotificationData?

    private fun sendNotification(notificationData: NotificationData) {
        val notificationManager = notificationData.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = 1
        val channelId = "channel-01"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_HIGH

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId, channelName, importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }


        val mBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(notificationData.smallIcon)
            .setContentTitle(notificationData.title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            .setContentText(notificationData.message)
            .setAutoCancel(notificationData.autoCancel)
            .setSound(notificationData.soundUri)
        notificationManager.notify(notificationId, mBuilder.build())
    }




}