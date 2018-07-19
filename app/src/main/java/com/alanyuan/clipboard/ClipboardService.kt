package com.alanyuan.clipboard

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.widget.Toast


class ClipboardService : Service() {
    val notificationID: Int = 9073

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.addPrimaryClipChangedListener {
            var clipData = clipboard.primaryClip
            if (clipData != null && clipData.getItemCount() > 0) {
                var clipText = clipData.getItemAt(0).coerceToText(applicationContext)
                Toast.makeText(baseContext, "Copy:\n$clipText", Toast.LENGTH_LONG).show()
            }
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val resultIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, resultIntent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val notification = NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(Color.YELLOW)
                .setContentTitle("Paster Title")
                .setContentText("Paster Content")
                .setContentIntent(pendingIntent)
                .build()

        startForeground(notificationID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, Constants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        channel.setDescription(Constants.NOTIFICATION_CHANNEL_NAME);
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return Constants.NOTIFICATION_CHANNEL_ID
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}