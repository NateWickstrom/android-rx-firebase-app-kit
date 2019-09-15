package media.pixi.appkit.data.followers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


class FirebaseFollowersProvider: FollowersProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun follow(userId: String): Completable {
        val map = mapOf(USER_ID to false)
        return getUserIdSingle().map { getDoc(it, userId) }
            .flatMapCompletable { doc -> RxFirestore.setDocument(doc, map) }
    }

    override fun unFollow(userId: String): Completable {
        return getUserIdSingle().map { getDoc(it, userId) }
            .flatMapCompletable { doc -> RxFirestore.deleteDocument(doc) }    }

    override fun getMyFollowers(): Flowable<List<String>> {
        return getUserIdSingle()
            .toFlowable()
            .flatMap { getFollowers(it) }
    }

    override fun getFollowers(userId: String): Flowable<List<String>> {
        val collection = firestore.collection(PEOPLE).document(userId).collection(FOLLOWERS)
        return RxFirestore.getCollection(collection)
            .map { snap -> toList(snap) }
            .toFlowable()
    }

    private fun toList(query: QuerySnapshot): List<String> {
        return query.documents.map { it.id }
    }

    private fun getUserIdSingle(): Single<String> {
        return Single.fromCallable {
            auth.currentUser?.uid ?: throw IllegalArgumentException("No user Id found.")
        }
    }

    private fun getDoc(followerUserId: String, followedUserId: String): DocumentReference {
        return firestore.collection(PEOPLE)
            .document(followedUserId)
            .collection(FOLLOWERS)
            .document(followerUserId)
    }

    companion object {
        // people // userId // followers // userID // <blank>
        private const val PEOPLE = "people"
        private const val FOLLOWERS = "followers"

        private const val USER_ID = "user_id"
    }
}