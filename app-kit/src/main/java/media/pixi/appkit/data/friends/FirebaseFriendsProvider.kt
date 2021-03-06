package media.pixi.appkit.data.friends

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Flowable


class FirebaseFriendsProvider: FriendsProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun getFriendsForUser(userId: String): Flowable<List<String>> {
        val ref = firestore.
            collection(FRIENDS)
            .document(userId)
            .collection(FRIENDS)

        return RxFirestore.getCollection(ref)
            .toFlowable()
            .map { toList(it) }
    }

    override fun getFriends(): Flowable<List<String>> {
        val currentUserId = auth.currentUser!!.uid
        return getFriendsForUser(currentUserId)
    }

    private fun toList(snapshot: QuerySnapshot): List<String> {
        return snapshot.documents.map { it.id }
    }

    companion object {
        private const val FRIENDS = "friends"
    }
}