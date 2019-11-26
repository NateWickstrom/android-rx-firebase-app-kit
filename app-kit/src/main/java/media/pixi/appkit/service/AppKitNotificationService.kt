package media.pixi.appkit.service

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import media.pixi.appkit.AppKitInjector
import media.pixi.appkit.data.MessageBus
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import media.pixi.appkit.domain.notifications.InAppNotificationManager
import timber.log.Timber
import javax.inject.Inject

class AppKitNotificationService: FirebaseMessagingService() {

    @Inject lateinit var devicesProvider: DevicesProvider
    @Inject lateinit var authProvider: AuthProvider
    @Inject lateinit var messageBus: MessageBus
    @Inject lateinit var notificationManager: InAppNotificationManager

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
        val title = remoteMessage.notification?.title!!
        val content = remoteMessage.notification?.body!!
        val priority = remoteMessage.priority

        val data = remoteMessage.data
        val notificationId = data[NOTIFICATION_ID]
        notificationManager.sendNotification(notificationId!!)
    }

    companion object {
        private const val NOTIFICATION_ID = "notificationId"
    }

}