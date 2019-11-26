package media.pixi.appkit.data.notifications

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable

class FirebaseNotificationProvider: NotificationProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun getNotifications(): Flowable<List<NotificationEntity>> {
        val userId = auth.currentUser!!.uid
        val ref = firestore.collection(NOTIFICATIONS)
            .document(userId).collection(NOTIFICATIONS)
            .orderBy(TIMESTAMP)

        return RxFirestore.getCollection(ref)
            .toFlowable()
            .map { toList(it) }
    }

    override fun deleteNotification(notificationId: String): Completable {
        val userId = auth.currentUser!!.uid
        val ref = firestore.
            collection(NOTIFICATIONS)
            .document(userId)
            .collection(NOTIFICATIONS)
            .document(notificationId)
        return RxFirestore.deleteDocument(ref)
    }

    private fun toList(snapshot: QuerySnapshot): List<NotificationEntity> {
        return snapshot.documents.map { toNotificationEntity(it) }
    }

    private fun toNotificationEntity(doc: DocumentSnapshot): NotificationEntity {
        val type = doc.getString("type") ?: "unkown"
        val noteType = NotificationType.toEnum(type)
        val userId = doc.getString("user_id") ?: "unknown"
        return NotificationEntity(type = noteType, id = doc.id, userId = userId)
    }

    companion object {
        private const val NOTIFICATIONS = "notifications"

        private const val TIMESTAMP = "timestamp"

    }
}