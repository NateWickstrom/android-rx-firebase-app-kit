package media.pixi.appkit.domain.notifications

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import media.pixi.appkit.R
import media.pixi.appkit.data.notifications.NotificationEntity
import media.pixi.appkit.data.notifications.NotificationProvider
import media.pixi.appkit.data.notifications.NotificationType
import timber.log.Timber
import javax.inject.Inject


class NotificationHelper @Inject constructor(private val context: Context,
                                             private val notificationProvider: NotificationProvider,
                                             private val getNotifications: GetNotifications) {

    private val notificationManager = NotificationManagerCompat.from(context)

    @WorkerThread
    fun sendNotification(notificationId: String) {
        val notificationEntity = getNotifications.getNotification(notificationId).blockingFirst()

        val notification = NotificationCompat.Builder(context, getChannelId(notificationEntity))
                .setSmallIcon(R.drawable.ic_build_24px)
                .setTicker(notificationEntity.title)
                .setContentText(notificationEntity.subtitle)
                .setContentTitle(notificationEntity.title)
                .setAutoCancel(true)
                //.setLargeIcon(thumbnailBitmap)
                .setContentIntent(intent())
                .setWhen(System.currentTimeMillis())
                .setPriority(0)
                .build()

        createNotification(getIntId(notificationEntity.entity), notification)
    }

    @WorkerThread
    fun clearNotification() {
//        val notifications = notificationProvider.getNotifications().blockingFirst()
//        notifications.forEach {
//            notificationManager.cancel(getIntId(it))
//        }
    }

    private fun createNotification(id: Int, notification: Notification) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createGeneralNotificationChannel()
            createFriendRequestNotificationChannel()
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
    private fun createGeneralNotificationChannel() {
        if (notificationManager.getNotificationChannel(PUSH_DEFAULT_CHANNEL_ID) != null) return

        val name = context.getString(R.string.general_notification_channel_title)
        val description = context.getString(R.string.general_notification_channel_description)
        val channel = NotificationChannel(PUSH_DEFAULT_CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)

        createNotificationChannel(description, channel)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createFriendRequestNotificationChannel() {
        if (notificationManager.getNotificationChannel(FRIEND_REQUESTS_CHANNEL_ID) != null) return

        val name = context.getString(R.string.friend_request_notification_channel_title)
        val description = context.getString(R.string.friend_request_notification_channel_description)
        val channel = NotificationChannel(FRIEND_REQUESTS_CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)

        createNotificationChannel(description, channel)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(description: String, channel: NotificationChannel) {
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = R.color.colorPrimary
        channel.setShowBadge(true)

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager.createNotificationChannel(channel)
    }

    private fun getIntId(notification: NotificationEntity): Int {
        return notification.id.hashCode()
    }

    private fun getChannelId(notification: MyNotification): String {
        return when (notification.entity.type) {
            NotificationType.NEW_FRIEND,
            NotificationType.FRIEND_REQUEST -> FRIEND_REQUESTS_CHANNEL_ID
            NotificationType.UNKNOWN -> PUSH_DEFAULT_CHANNEL_ID
        }
    }

    companion object {
        const val PUSH_DEFAULT_CHANNEL_ID = "general_notifications"
        const val FRIEND_REQUESTS_CHANNEL_ID = "friend_request_notifications"
    }
}