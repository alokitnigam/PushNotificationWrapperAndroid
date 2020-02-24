package com.example.pushnotificationsdk.Models

import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import com.example.pushnotificationssdk.R


data class NotificationData(val context: Context,
                            val title: String,
                            val message: String,
                            val soundUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                            val smallIcon: Int = R.drawable.baseline_notifications_black,
                            val autoCancel: Boolean = true,
                            val channelId: String = context.getString(R.string.channel_default),
                            var color: Int? = null,
                            val colorize: Boolean = false,
                            val contentInfo: String? = null,
                            val category: String? = null,
                            val timeoutAfter: Long? = null,
                            val subtext: String? = null,
                            val publicVersion: NotificationData? = null,
                            val notificationId: Int? = null,
                            val pendingIntent: PendingIntent? = null)

