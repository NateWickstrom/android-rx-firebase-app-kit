package media.pixi.appkit.service

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Notification
import android.graphics.Bitmap
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

class AppKitNotificationService: FirebaseMessagingService() {

    @Inject lateinit var devicesProvider: DevicesProvider
    @Inject lateinit var authProvider: AuthProvider
    @Inject lateinit var messageBus: MessageBus

    override fun onCreate() {
        super.onCreate()
        getInjector().inject(this)
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
        //remoteMessage.
    }

//    private fun sendNotification(remoteMessage: RemoteMessage) {
//        val title =
//            getSafeTitle(mContext.getString(R.string.kototoro_notification_draft_reminder_title))
//        val content = mContext.getString(R.string.kototoro_notification_draft_reminder_content)
//
//        val notification =
//            NotificationCompat.Builder(applicationContext, KototoroConstants.PUSH_DEFAULT_CHANNEL_ID)
//                .setSmallIcon(R.drawable.kototoro_system_tray_notification_icon)
//                .setTicker(content)
//                .setContentText(content)
//                .setContentTitle(title)
//                .setAutoCancel(true)
//                .setLargeIcon(thumbnailBitmap)
//                .setContentIntent(getCameraLaunchIntent())
//                .setWhen(mClock.now())
//                .setPriority(getNotificationsPriority())
//                .build()
//
//        createNotification(DRAFT_REMINDER_NOTIFICATION_ID, notification)
//    }
//
//    private fun createNotification(id: Int, notification: Notification) {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createNotificationChannel()
//        }
//
//        application.getSystemService()
//        mNotificationManager.notify(id, notification)
//    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
//        val name = getString(R.string.kototoro_general_notifications_channel_title)
//        val description = getString(R.string.kototoro_general_notifications_channel_discription)
//        val channel = NotificationChannel(
//            KototoroConstants.PUSH_DEFAULT_CHANNEL_ID, name, getNotificationsPriority()
//        )
//        channel.description = description
//        channel.enableLights(true)
//        channel.lightColor = R.color.kototoro_theme_purple
//        channel.setShowBadge(true)
//
//        // Register the channel with the system; you can't change the importance
//        // or other notification behaviors after this
//        mNotificationManager.createNotificationChannel(channel)
    }
}