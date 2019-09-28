package media.pixi.appkit.service

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import media.pixi.appkit.AppKitInjector
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import timber.log.Timber
import javax.inject.Inject

class AppKitNotificationService: FirebaseMessagingService() {

    @Inject lateinit var devicesProvider: DevicesProvider
    @Inject lateinit var authProvider: AuthProvider

    override fun onCreate() {
        super.onCreate()
        getInjector().inject(this)
    }

    @WorkerThread
    override fun onNewToken(token: String) {
        super.onNewToken(token)
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
}