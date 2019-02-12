package media.pixi.appkit.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class FirebaseAuthProvider: AuthProvider {

    private val loginSubject: PublishSubject<Boolean> = PublishSubject.create()
    private val authUserModelSubject: PublishSubject<AuthUserModel> = PublishSubject.create()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var user: AuthUserModel? = null

    override fun getUser(): AuthUserModel? {
        val firebaseUser = FirebaseAuth.getInstance().currentUser ?: return null

        val username = getUsername(firebaseUser)

        return AuthUserModel(
            firebaseUser.uid,
            firebaseUser.email ?: "",
            username,
            firebaseUser.photoUrl?.toString() ?: "",
            firebaseUser.phoneNumber ?: "",
            firebaseUser.isEmailVerified)
    }

    override fun isSignedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user != null
    }

    override fun signOut() {
        user = null
        FirebaseAuth.getInstance().signOut()
        this.loginSubject.onNext(false)
    }

    override fun observerAuthState(): Observable<Boolean> {
        return RxFirebaseAuth.observeAuthState(FirebaseAuth.getInstance())
                .map { auth -> auth.currentUser != null }
    }

    override fun signIn(email: String, password: String): Completable {
        return RxFirebaseAuth.signInWithEmailAndPassword(auth, email, password)
            .map { it.user }
            .map { toUserModel(it) }
            .map { updateUser(it) }
            .ignoreElement()
    }

    override fun signUp(email: String, password: String): Completable {
        return RxFirebaseAuth.createUserWithEmailAndPassword(auth, email, password)
            .map { it.user }
            .map { toUserModel(it) }
            .map { updateUser(it) }
            .ignoreElement()
    }

    override fun updateEmail(email: String, password: String): Completable {
        if (user == null) return Completable.error(IllegalArgumentException("No User"))

        return RxFirebaseAuth.signInWithEmailAndPassword(auth, user!!.email, password)
            .flatMapCompletable { RxFirebaseUser.updateEmail(it.user, email) }
    }

    override fun updatePassword(newPassword: String, oldPassword: String): Completable {
        if (user == null) return Completable.error(IllegalArgumentException("No User"))

        return RxFirebaseAuth.signInWithEmailAndPassword(auth, user!!.email, oldPassword)
            .flatMapCompletable { RxFirebaseUser.updatePassword(it.user, newPassword) }
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
        return RxFirebaseAuth.signInWithEmailAndPassword(auth, user!!.email, password)
            .flatMapCompletable { RxFirebaseUser.delete(it.user) }
    }

    private fun getUsername(firebaseUser: FirebaseUser): String {
        if (!firebaseUser.displayName.isNullOrBlank()) return firebaseUser.displayName!!

        if (!firebaseUser.email.isNullOrBlank()) return firebaseUser.email!!.substringBefore("@")

        return ""
    }

    private fun toUserModel(firebaseUser: FirebaseUser): AuthUserModel {
        return AuthUserModel(
            id = firebaseUser.uid,
            username = firebaseUser.displayName ?: "",
            imageUrl = firebaseUser.photoUrl.toString(),
            email = firebaseUser.email ?: "",
            phoneNumber = firebaseUser.phoneNumber ?: "",
            verifiedEmail = firebaseUser.isEmailVerified)
    }

    private fun updateUser(user: AuthUserModel?) {
        user?.let {
            this.user = user
            this.loginSubject.onNext(true)
            this.authUserModelSubject.onNext(user)
        }
    }

    companion object {
        private const val PEOPLE = "people"
        private const val FIRSTNAME = "firstname"
        private const val LASTNAME = "lastname"
        private const val USERNAME = "username"

    }
}