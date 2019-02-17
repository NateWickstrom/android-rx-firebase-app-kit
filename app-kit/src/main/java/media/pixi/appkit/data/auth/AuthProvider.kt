package media.pixi.appkit.data.auth

import io.reactivex.Completable
import io.reactivex.Flowable


interface AuthProvider {

    fun getUserId(): String?

    fun isSignedIn(): Boolean

    fun signOut()

    fun observerAuthState(): Flowable<Boolean>

    fun observerLoggedInUser(): Flowable<AuthUserModel>

    fun signIn(email: String, password: String): Completable

    fun signUp(email: String, password: String): Completable

    fun updateProfileImage(url: String): Completable

    fun updateUsername(name: String): Completable

    fun updateFirstName(name: String): Completable

    fun updateLastName(name: String): Completable

    fun updateEmail(email: String, password: String): Completable

    fun updatePassword(newPassword: String, oldPassword: String): Completable

    fun sendEmailVerification(): Completable

    fun sendPasswordResetEmail(email: String): Completable

    fun deleteAccount(password: String): Completable

}