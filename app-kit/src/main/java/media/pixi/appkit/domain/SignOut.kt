package media.pixi.appkit.domain

import io.reactivex.Completable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import media.pixi.appkit.domain.notifications.NotificationHelper
import javax.inject.Inject

class SignOut @Inject constructor(private var notificationHelper: NotificationHelper,
                                  private var authProvider: AuthProvider,
                                  private var devicesProvider: DevicesProvider) {

    fun signOut(): Completable {
        return devicesProvider.unregisterDevice()
            .andThen(signOutCompletable())
            .doOnError { devicesProvider.registerDevice() }
    }

    private fun signOutCompletable(): Completable {
        return Completable.fromCallable {
            notificationHelper.clearNotification()
            authProvider.signOut()
        }
    }
}