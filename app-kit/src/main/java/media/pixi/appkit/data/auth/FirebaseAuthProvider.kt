package media.pixi.appkit.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseUser
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class FirebaseAuthProvider: AuthProvider {

    private val loginSubject: PublishSubject<Boolean> = PublishSubject.create()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun getUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    override fun isSignedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user != null
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
        this.loginSubject.onNext(false)
    }

    override fun observerAuthState(): Flowable<Boolean> {
        return RxFirebaseAuth.observeAuthState(FirebaseAuth.getInstance())
                .map { auth -> auth.currentUser != null }
                .toFlowable(BackpressureStrategy.LATEST)
    }

    override fun observerLoggedInUser(): Flowable<AuthUserModel> {
        return RxFirebaseAuth.observeAuthState(FirebaseAuth.getInstance())
            .filter { it.currentUser != null }
            .toFlowable(BackpressureStrategy.BUFFER)
            .concatMap { auth -> observerLoggedInUser(auth.currentUser!!) }
    }

    private fun observerLoggedInUser(firebaseUser: FirebaseUser): Flowable<AuthUserModel> {
        val uid = firebaseUser.uid
        val ref = firestore.collection(PEOPLE).document(uid)
        return RxFirestore.observeDocumentRef(ref).map { this.toUserModel(firebaseUser, it) }
    }

    override fun signIn(email: String, password: String): Completable {
        return RxFirebaseAuth.signInWithEmailAndPassword(auth, email, password)
            // TODO bug with completion before signed in user actually available
            .delay(SIGNIN_DELAY_SECONDS, TimeUnit.SECONDS)
            .ignoreElement()
    }

    override fun signUp(email: String, password: String): Completable {
        return RxFirebaseAuth.createUserWithEmailAndPassword(auth, email, password)
            // TODO bug with completion before signed in user actually available
            .delay(SIGNIN_DELAY_SECONDS, TimeUnit.SECONDS)
            .ignoreElement()
    }

    override fun updateEmail(email: String, password: String): Completable {
        if (getUser() == null) return Completable.error(IllegalArgumentException("No User"))

        return RxFirebaseAuth.signInWithEmailAndPassword(auth, getUser()!!.email!!, password)
            .flatMapCompletable { RxFirebaseUser.updateEmail(it.user!!, email) }
    }

    override fun updatePassword(newPassword: String, oldPassword: String): Completable {
        if (getUser() == null) return Completable.error(IllegalArgumentException("No User"))

        return RxFirebaseAuth.signInWithEmailAndPassword(auth, getUser()!!.email!!, oldPassword)
            .flatMapCompletable { RxFirebaseUser.updatePassword(it.user!!, newPassword) }
    }

    override fun updateUsername(name: String): Completable {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isEmpty()) return Completable.error(IllegalArgumentException("No User"))

        val ref = firestore.collection(PEOPLE).document(uid)
        return RxFirestore.updateDocument(ref, USERNAME, name)
    }

    override fun updateFirstName(name: String): Completable {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isEmpty()) return Completable.error(IllegalArgumentException("No User"))

        val ref = firestore.collection(PEOPLE).document(uid)
        return RxFirestore.updateDocument(ref, FIRSTNAME, name)
    }

    override fun updateLastName(name: String): Completable {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isEmpty()) return Completable.error(IllegalArgumentException("No User"))

        val ref = firestore.collection(PEOPLE).document(uid)
        return RxFirestore.updateDocument(ref, LASTNAME, name)
    }

    override fun updateProfileImage(url: String): Completable {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isEmpty()) return Completable.error(IllegalArgumentException("No User"))

        val ref = firestore.collection(PEOPLE).document(uid)
        return RxFirestore.updateDocument(ref, IMAGE_URL, url)
    }

    override fun sendEmailVerification(): Completable {
        auth.currentUser?.let {
            return RxFirebaseUser.sendEmailVerification(it)
        }
        return Completable.error(IllegalArgumentException("No User"))
    }

    override fun sendPasswordResetEmail(email: String): Completable {
        return RxFirebaseAuth.sendPasswordResetEmail(auth, email)
    }

    override fun deleteAccount(password: String): Completable {
        // todo handle non-email accounts
        return RxFirebaseAuth.signInWithEmailAndPassword(auth, getUser()!!.email!!, password)
            .flatMapCompletable { RxFirebaseUser.delete(it.user!!) }
    }

    private fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    private fun toUserModel(firebaseUser: FirebaseUser, snapshot: DocumentSnapshot): AuthUserModel {
        return AuthUserModel(
            id = firebaseUser.uid,
            email = firebaseUser.email ?: "",
            username = snapshot.get(USERNAME) as String? ?: "",
            firstName = snapshot.get(FIRSTNAME) as String? ?: "",
            lastName = snapshot.get(LASTNAME) as String? ?: "",
            imageUrl = snapshot.get(IMAGE_URL) as String? ?: "",
            phoneNumber = firebaseUser.phoneNumber ?: "",
            verifiedEmail = firebaseUser.isEmailVerified)
    }

    companion object {
        private const val PEOPLE = "people"
        private const val FIRSTNAME = "firstname"
        private const val LASTNAME = "lastname"
        private const val USERNAME = "username"
        private const val IMAGE_URL = "thumbnailUrl"

        private const val SIGNIN_DELAY_SECONDS = 2L
    }
}
