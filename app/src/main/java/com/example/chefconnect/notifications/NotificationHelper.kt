package com.example.chefconnect.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
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

    suspend fun showFavoriteNotification(
        mealId: String,
        mealName: String,
        imageUrl: String
    ) {

        val bitmap = loadBitmap(imageUrl)

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

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(mealName)
            .setContentText("Guardado en favoritos")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (bitmap != null) {
            builder
                .setLargeIcon(bitmap) // miniatura
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)       // imagen grande
                        .bigLargeIcon(null as Bitmap?)
                )
        }

        NotificationManagerCompat.from(context)
            .notify(mealId.hashCode(), builder.build())
    }

    private suspend fun loadBitmap(url: String): Bitmap? {
        val loader = ImageLoader(context)

        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false)
            .build()

        val result = loader.execute(request)

        return if (result is SuccessResult) {
            (result.drawable as BitmapDrawable).bitmap
        } else null
    }
}