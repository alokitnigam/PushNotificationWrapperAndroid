package com.example.pushnotificationsdk

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId

class Notification private constructor(var context: Context)  {


     class Builder {
        private lateinit var context:Context

        fun init(context: Context) = apply {
            this.context = context
            Notification.context = context
            initApp()
        }
        fun build() = Notification(context)
         fun initApp(){
             FirebaseApp.initializeApp(context)
         }
    }


    companion object {
        private  var token:String? = null
        var subscribed = false
        lateinit var context:Context

        fun subscribe(onFailure: (Exception?) -> Unit){
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        onFailure(task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token
                    if (token != null) {
                        this.token = token
                    }
                    subscribed = true
                })
        }

        fun getPermissionStatus():Boolean{
            return ContextCompat.checkSelfPermission(context,android.Manifest.permission.INTERNET)== 0
        }

        fun getSubscriptionStatus():Boolean{
            return subscribed
        }

        fun getSubscriptionToken(param: TokenUpdated){
            if (token == null)
                FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token
                    if (token != null) {
                        this.token = token
                        param.onRecievedToken(token!!)
                    }
                })
            else
                param.onRecievedToken(token!!)

        }


        fun sendRegistrationToServer(token: String) {
            this.token = token
        }

    }


    interface TokenUpdated{
        fun onRecievedToken(token : String)
    }

}