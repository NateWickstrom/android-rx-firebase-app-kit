package media.pixi.appkit.data.notifications

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Flowable

class FirebaseNotificationProvider(): NotificationProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun getNotifications(): Flowable<List<String>> {
        val userId = auth.currentUser!!.uid
        val ref = firestore.collection(NOTIFICATIONS)
            .document(userId).collection(NOTIFICATIONS)
        return RxFirestore.getCollection(ref)
            .toFlowable()
            .map { toList(it) }
    }

    private fun toList(snapshot: QuerySnapshot): List<String> {
        return snapshot.documents.map { it.id }
    }

    companion object {
        private const val NOTIFICATIONS = "notification"
    }
}