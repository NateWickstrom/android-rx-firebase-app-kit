package media.pixi.appkit.domain

import android.content.Context
import io.reactivex.Completable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import javax.inject.Inject

class SignOut @Inject constructor(private var context: Context,
                                  private var authProvider: AuthProvider,
                                  private var devicesProvider: DevicesProvider) {

    fun signOut(): Completable {
        return devicesProvider.unregisterDevice()
                // todo clear notifications
            .andThen(signOutCompletable())
            .doOnError { devicesProvider.registerDevice() }
    }

    private fun signOutCompletable(): Completable {
        return Completable.fromCallable {
            authProvider.signOut()
        }
    }
}