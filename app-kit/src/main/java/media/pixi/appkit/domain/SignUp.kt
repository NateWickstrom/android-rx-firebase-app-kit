package media.pixi.appkit.domain

import io.reactivex.Completable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import javax.inject.Inject

class SignUp @Inject constructor(private var authProvider: AuthProvider,
                                 private var devicesProvider: DevicesProvider
) {

    fun signUp(email: String, password: String): Completable {
        return authProvider.signUp(email, password)
            .andThen(devicesProvider.registerDevice())
    }
}