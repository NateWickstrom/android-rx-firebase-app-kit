package media.pixi.appkit.domain

import io.reactivex.Completable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignIn @Inject constructor(private var authProvider: AuthProvider,
                                 private var devicesProvider: DevicesProvider) {

    fun signIn(email: String, password: String): Completable {
        return authProvider.signIn(email, password)
            // TODO bug with completion before signed in user actually available
            .delay(DELAY, TimeUnit.SECONDS)
            .andThen(devicesProvider.registerDevice())
            .doOnError { authProvider.signOut() }
    }

    companion object {
        private const val DELAY = 1L
    }
}