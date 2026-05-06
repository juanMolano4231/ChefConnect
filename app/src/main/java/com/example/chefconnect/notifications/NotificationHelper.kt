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

    init {
        createChannels()
    }

    private fun createChannels() {
        val manager = context.getSystemService(NotificationManager::class.java)

        val channels = listOf(

            NotificationChannel(
                NotificationChannels.INFO,
                "Info",
                NotificationManager.IMPORTANCE_LOW
            ),

            NotificationChannel(
                NotificationChannels.UPDATES,
                "Updates",
                NotificationManager.IMPORTANCE_LOW
            ),

            NotificationChannel(
                NotificationChannels.SEARCH,
                "Search",
                NotificationManager.IMPORTANCE_LOW
            ),

            NotificationChannel(
                NotificationChannels.FAVORITES,
                "Favorites",
                NotificationManager.IMPORTANCE_DEFAULT
            ),

            NotificationChannel(
                NotificationChannels.ERRORS,
                "Errors",
                NotificationManager.IMPORTANCE_HIGH
            )
        )

        channels.forEach { manager.createNotificationChannel(it) }
    }

    suspend fun showFavoriteNotification(
        mealId: String,
        mealName: String,
        message: String,
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

        val builder = NotificationCompat.Builder(
            context,
            NotificationChannels.FAVORITES
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(mealName)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (bitmap != null) {
            builder
                .setLargeIcon(bitmap)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null as Bitmap?)
                )
        }

        NotificationManagerCompat.from(context)
            .notify(mealId.hashCode(), builder.build())
    }

    suspend fun showErrorNotification(message: String) {

        val builder = NotificationCompat.Builder(
            context,
            NotificationChannels.ERRORS
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Error")
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(context)
            .notify(System.currentTimeMillis().toInt(), builder.build())
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