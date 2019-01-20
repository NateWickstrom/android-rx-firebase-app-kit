package media.pixi.rx.firebase.auth.kit.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface AuthProvider {

    fun getUser(): AuthUserModel?

    fun isSignedIn(): Boolean

    fun signOut()

    fun observerAuthState(): Observable<Boolean>

    fun signIn(email: String, password: String): Completable

    fun signUp(email: String, password: String): Completable

    fun updateProfileImage(url: String): Completable

    fun updateDisplayname(name: String): Completable

    fun updateEmail(email: String, password: String): Completable

    fun updatePassword(newPassword: String, oldPassword: String): Completable

    fun sendEmailVerification(): Completable

    fun sendPasswordResetEmail(email: String): Completable

    fun deleteAccount(password: String): Completable

}