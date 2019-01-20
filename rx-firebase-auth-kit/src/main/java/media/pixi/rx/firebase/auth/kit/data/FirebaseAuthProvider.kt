package media.pixi.rx.firebase.auth.kit.data

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

import javax.inject.Inject
import javax.inject.Singleton

import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.lang.IllegalArgumentException


@Singleton
class FirebaseAuthProvider @Inject constructor() : AuthProvider {

    private val loginSubject: PublishSubject<Boolean> = PublishSubject.create()
    private val authUserModelSubject: PublishSubject<AuthUserModel> = PublishSubject.create()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var user: AuthUserModel? = null

    override fun getUser(): AuthUserModel? {
        val firebaseUser = FirebaseAuth.getInstance().currentUser ?: return null

        return AuthUserModel(
            firebaseUser.uid,
            firebaseUser.email ?: "",
            firebaseUser.displayName ?: "",
            firebaseUser.photoUrl?.toString() ?: "",
            firebaseUser.phoneNumber ?: "",
            firebaseUser.isEmailVerified)
    }

    override fun isSignedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user != null
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
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

    override fun updateProfileImage(url: String): Completable {
        if (auth.currentUser == null) return Completable.error(IllegalArgumentException("No User"))

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(url))
            .build()

        return  RxFirebaseUser.updateProfile(auth.currentUser!!, profileUpdates)    }

    override fun updateDisplayname(name: String): Completable {
        if (auth.currentUser == null) return Completable.error(IllegalArgumentException("No User"))

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()

        return  RxFirebaseUser.updateProfile(auth.currentUser!!, profileUpdates)
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
}
