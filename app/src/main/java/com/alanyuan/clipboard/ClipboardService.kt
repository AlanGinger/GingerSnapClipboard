package com.alanyuan.clipboard

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.alanyuan.clipboard.RealmHelper.Companion.RealmConfig
import io.realm.Realm


class ClipboardService : Service() {
    val notificationID: Int = 9073

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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
                .setContentTitle("Paster Title")
                .setContentText("Paster Content")
                .setContentIntent(pendingIntent)
                .build()

        startForeground(notificationID, notification)

        addClipboardListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, Constants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        channel.description = Constants.NOTIFICATION_CHANNEL_NAME
        channel.enableLights(true)
        channel.lightColor = Color.BLUE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return Constants.NOTIFICATION_CHANNEL_ID
    }

    private fun addClipboardListener() {
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.addPrimaryClipChangedListener {
            val clipData = clipboard.primaryClip
            if (clipData != null && clipData.itemCount > 0) {
                if (clipData.description.getMimeType(0) == ClipDescription.MIMETYPE_TEXT_PLAIN) {
                    val clipText = clipData.getItemAt(0).text
                    val clipDataBean = ClipboardData()
                    clipDataBean.clipTimeMillis = System.currentTimeMillis()
                    clipDataBean.clipText = clipText.toString()
                    clipDataBean.isHtml = false
                    saveData(clipDataBean)
                } else if (clipData.description.getMimeType(0) == ClipDescription.MIMETYPE_TEXT_HTML) {
                    val clipText = clipData.getItemAt(0).htmlText
                    val clipDataBean = ClipboardData()
                    clipDataBean.clipTimeMillis = System.currentTimeMillis()
                    clipDataBean.clipText = clipText.toString()
                    clipDataBean.isHtml = false
                    saveData(clipDataBean)
                }
            }
        }
    }

    private fun saveData(clipDataBean: ClipboardData) {
        val realm: Realm = Realm.getInstance(RealmConfig)
        realm.beginTransaction()
        realm.copyToRealm(clipDataBean)
        realm.commitTransaction()
        realm.close()
    }

}