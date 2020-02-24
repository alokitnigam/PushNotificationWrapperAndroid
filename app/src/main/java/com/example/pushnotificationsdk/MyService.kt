package com.example.pushnotificationsdk

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.pushnotificationsdk.Models.NotificationData
import com.example.pushnotificationsdk.Models.RemoteMessageData

class MyService : PushMessengingService() {

    override fun handleNotificationData(context:Context, remoteMessageData: RemoteMessageData): NotificationData? {

        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.putExtra("type", remoteMessageData.title ?: "Default Title"+" "
        +remoteMessageData.body ?: "Default Message")
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationData(
                context = context,
                title = remoteMessageData.title ?: "Default Title",
                message = remoteMessageData.body ?: "Default Message",
                channelId = "channel_global_notifications",     // needed for SDK >= Android "O" (Oreo)
                autoCancel = true,
                pendingIntent = contentIntent)
    }
}