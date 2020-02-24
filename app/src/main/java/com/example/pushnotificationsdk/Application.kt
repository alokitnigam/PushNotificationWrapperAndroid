package com.example.pushnotificationsdk

import android.app.Application

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        val notification = Notification.Builder()
            .init(context = this)
            .build()
    }
}