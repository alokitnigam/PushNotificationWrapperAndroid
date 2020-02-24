package com.example.pushnotificationsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(Notification.getPermissionStatus() && !Notification.getSubscriptionStatus()){
            Notification.subscribe(
                onFailure = {
                    val msg = "onFailure - error during registration: ${it?.message}"
                })

        }

        Notification.getSubscriptionToken(object : Notification.TokenUpdated{
            override fun onRecievedToken(token: String) {
                Log.i("----->",token+"")

            }

        })







    }
}
