package media.pixi.appkit.data.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import durdinapps.rxfirebase2.RxFirebaseFunctions
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable

class FirebaseUserProfileProvider: UserProfileProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val functions = FirebaseFunctions.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun observerUserProfile(userId: String): Flowable<UserProfile> {
        val ref = firestore.collection(PEOPLE).document(userId)
        return RxFirestore.observeDocumentRef(ref).map { this.toUserProfile(it) }
    }

    override fun addFriend(userId: String): Completable {
        val request = request(userId)
        return RxFirebaseFunctions.getHttpsCallable(functions, ADD_FRIEND, request).ignoreElement()
    }

    override fun unFriend(userId: String): Completable {
        val request = request(userId)
        return RxFirebaseFunctions.getHttpsCallable(functions, UNFRIEND, request).ignoreElement()
    }

    override fun block(userId: String): Completable {
        val request = request(userId)
        return RxFirebaseFunctions.getHttpsCallable(functions, BLOCK, request).ignoreElement()
    }

    private fun request(userId: String): Request {
        val currentUserId = auth.currentUser?.uid ?: throw IllegalAccessError("No logged in used")
        return Request(requester = currentUserId, requestee = userId)
    }

    private fun toUserProfile(snapshot: DocumentSnapshot): UserProfile {
        return UserProfile(
            id = snapshot.id,
            isFriend = false,
            isBlocked = false,
            friendCount = 0,
            username = snapshot.get(USERNAME) as String,
            firstName = snapshot.get(FIRSTNAME) as String,
            lastName = snapshot.get(LASTNAME) as String,
            imageUrl = snapshot.get(IMAGE_URL) as String)
    }

    companion object {
        private const val PEOPLE = "people"
        private const val FIRSTNAME = "firstname"
        private const val LASTNAME = "lastname"
        private const val USERNAME = "username"
        private const val IMAGE_URL = "imageUrl"

        private const val ADD_FRIEND = "addFriend"
        private const val UNFRIEND = "unFriend"
        private const val BLOCK = "block"

    }
}