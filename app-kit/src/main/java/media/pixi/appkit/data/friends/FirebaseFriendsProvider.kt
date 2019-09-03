package media.pixi.appkit.data.friends

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Flowable


class FirebaseFriendsProvider: FriendsProvider {

    private val firestore = FirebaseFirestore.getInstance()

    override fun getFriendsForUser(userId: String): Flowable<FriendsResult> {
        val ref = firestore.collection(FRIENDS)
            .document(userId).collection(FRIENDS)
        return RxFirestore.getCollection(ref)
            .toFlowable()
            .map { toList(it) }
    }

    private fun toList(snapshot: QuerySnapshot): FriendsResult {
        val result =  snapshot.documents.map { it.id }
        return FriendsResult(ids = result)
    }

    companion object {
        private const val FRIENDS = "friends"
    }
}