package media.pixi.appkit.service.messages

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import media.pixi.appkit.AppKitInjector
import timber.log.Timber
import javax.inject.Inject

class FirebaseCloudMessagingService: FirebaseMessagingService() {

    @Inject lateinit var helper: FirebaseCloudMessagingHelper

    override fun onCreate() {
        super.onCreate()
        getInjector().inject(this)
    }

    @WorkerThread
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        helper.onNewToken(token)
    }

    @WorkerThread
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        helper.onMessageReceived(remoteMessage)
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