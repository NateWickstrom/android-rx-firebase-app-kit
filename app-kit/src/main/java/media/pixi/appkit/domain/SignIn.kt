package media.pixi.appkit.domain

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignIn @Inject constructor(private var authProvider: AuthProvider,
                                 private var devicesProvider: DevicesProvider) {

    fun signIn(email: String, password: String): Completable {
        return authProvider.signIn(email, password)
            .andThen(Completable.fromCallable { getUser() })
            .andThen(devicesProvider.registerDevice())
            .doOnError { authProvider.signOut() }
    }

    private fun getUser(): FirebaseUser {
        return GetUserFuture().get(TIMEOUT, TimeUnit.SECONDS) ?: throw IllegalArgumentException("Failed to get FirebaseUser")
    }
    
    companion object {
        private const val TIMEOUT = 10L
    }
}