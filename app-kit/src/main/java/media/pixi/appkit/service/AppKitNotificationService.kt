package media.pixi.appkit.service

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import media.pixi.appkit.AppKitInjector
import media.pixi.appkit.R
import media.pixi.appkit.data.MessageBus
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import timber.log.Timber
import javax.inject.Inject
import androidx.core.app.NotificationManagerCompat
import android.app.PendingIntent

class AppKitNotificationService: FirebaseMessagingService() {

    @Inject lateinit var devicesProvider: DevicesProvider
    @Inject lateinit var authProvider: AuthProvider
    @Inject lateinit var messageBus: MessageBus

    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate() {
        super.onCreate()
        getInjector().inject(this)
        notificationManager = NotificationManagerCompat.from(this)
    }

    @WorkerThread
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //todo delete old token when replacing
        Timber.d("New Device Token: $token")
        devicesProvider.deviceId = token

        if (authProvider.isSignedIn()) {
            try {
                devicesProvider.registerDevice().blockingAwait()
            } catch (ex: Exception) {
                Timber.w("Trouble registering device")
            }
        }
    }

    @WorkerThread
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Timber.d("notification message received")
        remoteMessage?.let { if (messageBus.sendMessage(it).not()) {
                sendNotification(it)
            }
        }
    }

    @WorkerThread
    override fun onDeletedMessages() {
        super.onDeletedMessages()
        Timber.d("notification message deleted")
    }

    @SuppressLint("WrongConstant")
    private fun getInjector(): AppKitInjector {
        val injector = application.getSystemService(AppKitInjector.INJECTOR)
        return injector as AppKitInjector
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title
        val content = remoteMessage.notification?.body
        val priority = remoteMessage.priority

        val notification =
            NotificationCompat.Builder(applicationContext, PUSH_DEFAULT_CHANNEL_ID)
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

    private fun createNotification(id: Int, notification: Notification) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        notificationManager.notify(id, notification)
    }

    private fun intent(): PendingIntent {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        return PendingIntent.getActivity(this, 0, intent, 0)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (notificationManager.getNotificationChannel(PUSH_DEFAULT_CHANNEL_ID) != null) return

        val name = getString(R.string.notification_channel_title)
        val description = getString(R.string.notification_channel_description)
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