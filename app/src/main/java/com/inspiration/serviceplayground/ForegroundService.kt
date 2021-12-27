package com.inspiration.serviceplayground

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class ForegroundService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(this.javaClass.simpleName,"hello service")

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = "notification channel"
        val name = "notification name"
        val notificationDescription = "notification description"

        if (manager.getNotificationChannel(id) == null) {
            val mChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            mChannel.apply { description = notificationDescription }
            manager.createNotificationChannel(mChannel)
        }

        val notification = NotificationCompat.Builder(this, id).apply {
            setContentTitle("hello")
            setContentText("service")
            setSmallIcon(R.drawable.ic_launcher_background)
        }.build()

        startForeground(1, notification)

        GlobalScope.launch {
            delay(3000)
            stopForeground(Service.STOP_FOREGROUND_DETACH)
        }

        return super.onStartCommand(intent, flags, startId)
    }

}
