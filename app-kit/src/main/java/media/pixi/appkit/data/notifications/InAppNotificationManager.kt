package media.pixi.appkit.data.notifications

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import media.pixi.appkit.R
import javax.inject.Inject


class InAppNotificationManager @Inject constructor(private val context: Context) {

    private val notificationManager = NotificationManagerCompat.from(context)

    fun sendNotification(title: String, content: String, priority: Int) {

        val notification =
            NotificationCompat.Builder(context, PUSH_DEFAULT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_build_24px)
                .setTicker(content)
                .setContentText(content)
                .setContentTitle(title)
                .setAutoCancel(true)
                //.setLargeIcon(thumbnailBitmap)
                .setContentIntent(intent())
                .setWhen(System.currentTimeMillis())
                .setPriority(priority)
                .build()

        createNotification(PUSH_DEFAULT_ID, notification)
    }

    fun clearNotification() {
        notificationManager.cancel(PUSH_DEFAULT_ID)
    }

    private fun createNotification(id: Int, notification: Notification) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        notificationManager.notify(id, notification)
    }

    private fun intent(): PendingIntent {
        val packageManager = context.packageManager
        val packageName = context.packageName
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        return PendingIntent.getActivity(context, 0, intent, 0)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (notificationManager.getNotificationChannel(PUSH_DEFAULT_CHANNEL_ID) != null) return

        val name = context.getString(R.string.notification_channel_title)
        val description = context.getString(R.string.notification_channel_description)
        val channel = NotificationChannel(PUSH_DEFAULT_CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = R.color.colorPrimary
        channel.setShowBadge(true)

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val PUSH_DEFAULT_CHANNEL_ID = "general_notifications"
        const val PUSH_DEFAULT_ID = 99
    }
}