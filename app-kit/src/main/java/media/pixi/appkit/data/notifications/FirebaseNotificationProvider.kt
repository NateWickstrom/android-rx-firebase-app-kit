package media.pixi.appkit.data.notifications

import android.content.Context
import android.os.Build
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import media.pixi.appkit.data.devices.GetInstanceIdFuture

class FirebaseNotificationProvider(private val context: Context): NotificationProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun registerUser() {

        var user = auth.currentUser?.uid
        val model = Build.MODEL
        val token = GetInstanceIdFuture().get()?.token


    }

    override fun unregisterUser() {


    }

    companion object {
        private const val NOTIFICATIONS = "notifications"
    }
}