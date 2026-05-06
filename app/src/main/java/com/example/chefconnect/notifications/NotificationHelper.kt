package com.example.chefconnect.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.chefconnect.MainActivity
import com.example.chefconnect.R

class NotificationHelper(private val context: Context) {

    private val CHANNEL_ID = "chef_channel"

    init {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "ChefConnect",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun showFavoriteNotification(mealId: String, mealName: String) {

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("chefconnect://details/$mealId"),
            context,
            MainActivity::class.java
        )

        val pendingIntent = PendingIntent.getActivity(
            context,
            mealId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Guardado en favoritos")
            .setContentText(mealName)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(mealId.hashCode(), notification)
    }
}