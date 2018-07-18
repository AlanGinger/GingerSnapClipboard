package com.alanyuan.paster

import android.app.*
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.widget.Toast


class PasterService : Service() {
    val notificationID: Int = 9073

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.addPrimaryClipChangedListener {
            val a = clipboard.text.toString()
            Toast.makeText(baseContext, "Copy:\n$a", Toast.LENGTH_LONG).show()
        }
        return START_REDELIVER_INTENT
    }

    override fun onCreate() {
        super.onCreate()
        val resultIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, resultIntent, 0)

        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        } else {
            "PasterChannelName"
        }
        val notification: Notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Paster Title")
                .setContentText("Paster Content")
                .setContentIntent(pendingIntent)
                .build()

        startForeground(notificationID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channelId = "PasterChannelId"
        val channelName = "PasterChannelName"
        val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}