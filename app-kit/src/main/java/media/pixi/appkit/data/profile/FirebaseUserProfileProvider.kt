package media.pixi.appkit.data.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable


class FirebaseUserProfileProvider: UserProfileProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun observerUserProfile(userId: String): Flowable<UserProfile> {
        val ref = firestore.collection(PEOPLE).document(userId)
        return RxFirestore.observeDocumentRef(ref).map { toUserProfile(it) }
    }

    override fun addFriend(userId: String): Completable {
        val request = request(userId)
        val ref = firestore.collection(FRIEND_REQUEST).document()
        return RxFirestore.setDocument(ref, request)
    }

    override fun unFriend(userId: String): Completable {
        val request = request(userId)
        val ref = firestore.collection(UNFRIEND_REQUEST).document()
        return RxFirestore.setDocument(ref, request)
    }

    override fun block(userId: String): Completable {
        val request = request(userId)
        val ref = firestore.collection(BLOCKED_REQUEST).document()
        return RxFirestore.setDocument(ref, request)
    }

    override fun isFriend(userId: String): Flowable<Boolean> {
        val ref = firestore.collection(FRIENDS)
            .document(currentUserId()).collection(FRIENDS).document(userId)
        return RxFirestore.observeDocumentRef(ref).map { it.exists() }
    }

    override fun isBlocked(userId: String): Flowable<Boolean> {
        val ref = firestore.collection(SOCIAL)
            .document(currentUserId()).collection(BLOCKED).document(userId)
        return RxFirestore.observeDocumentRef(ref).map { it.exists() }    }

    private fun request(userId: String): Request {
        return Request(requester = currentUserId(), requestee = userId)
    }

    private fun currentUserId(): String {
        return auth.currentUser?.uid ?: throw IllegalAccessError("No logged in user")
    }

    private fun toUserProfile(snapshot: DocumentSnapshot): UserProfile {
        return UserProfile(
            id = snapshot.id,
            friendCount = 0,
            username = snapshot.get(USERNAME) as String,
            firstName = snapshot.get(FIRST_NAME) as String,
            lastName = snapshot.get(LAST_NAME) as String,
            imageUrl = snapshot.get(IMAGE_URL) as String)
    }

    companion object {
        private const val PEOPLE = "people"

        private const val FIRST_NAME = "firstname"
        private const val LAST_NAME = "lastname"
        private const val USERNAME = "username"
        private const val IMAGE_URL = "imageUrl"

        private const val SOCIAL = "social"
        private const val FRIENDS = "friends"
        private const val BLOCKED = "blocked"

        private const val FRIEND_REQUEST = "create_friend_request"
        private const val UNFRIEND_REQUEST = "unfriend_request"
        private const val BLOCKED_REQUEST = "block_request"
    }
}