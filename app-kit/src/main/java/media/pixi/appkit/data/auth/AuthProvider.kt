package media.pixi.appkit.data.auth

import io.reactivex.Completable
import io.reactivex.Observable


interface AuthProvider {

    fun getUser(): AuthUserModel?

    fun isSignedIn(): Boolean

    fun signOut()

    fun observerAuthState(): Observable<Boolean>

    fun signIn(email: String, password: String): Completable

    fun signUp(email: String, password: String): Completable

    fun updateEmail(email: String, password: String): Completable

    fun updatePassword(newPassword: String, oldPassword: String): Completable

    fun sendEmailVerification(): Completable

    fun sendPasswordResetEmail(email: String): Completable

    fun deleteAccount(password: String): Completable

}