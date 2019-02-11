package media.pixi.appkit.data.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable

class FirebaseCurrentUserProfileProvider: CurrentUserProfileProvider {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun observerProfile(): Flowable<UserProfile> {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isEmpty()) return Flowable.error(IllegalArgumentException("No User"))

        val ref = firestore.collection(PEOPLE).document(uid)

        return RxFirestore.observeDocumentRef(ref).map { this.toUserProfile(it) }
    }

    override fun updateUsername(name: String): Completable {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isEmpty()) return Completable.error(IllegalArgumentException("No User"))

        val ref = firestore.collection(PEOPLE).document(uid)
        return RxFirestore.updateDocument(ref, USERNAME, name)
    }

    override fun updateFirstname(name: String): Completable {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isEmpty()) return Completable.error(IllegalArgumentException("No User"))

        val ref = firestore.collection(PEOPLE).document(uid)
        return RxFirestore.updateDocument(ref, FIRSTNAME, name)
    }

    override fun updateLastname(name: String): Completable {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isEmpty()) return Completable.error(IllegalArgumentException("No User"))

        val ref = firestore.collection(PEOPLE).document(uid)
        return RxFirestore.updateDocument(ref, LASTNAME, name)
    }

    override fun updateProfileImage(url: String): Completable {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isEmpty()) return Completable.error(IllegalArgumentException("No User"))

        val ref = firestore.collection(PEOPLE).document(uid)
        return RxFirestore.updateDocument(ref, LASTNAME, IMAGE_URL)
    }

    private fun toUserProfile(snapshot: DocumentSnapshot): UserProfile {
        return UserProfile(
            id = snapshot.id,
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

    }
}