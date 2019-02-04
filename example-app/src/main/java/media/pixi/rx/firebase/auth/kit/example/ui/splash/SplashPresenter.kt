package media.pixi.rx.firebase.auth.kit.example.ui.splash

import android.app.Activity
import android.content.Intent
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rx.firebase.auth.kit.example.ui.splash.SplashContract.Companion.REQUEST_CODE
import media.pixi.rx.firebase.auth.kit.ui.AUTH_RESPONSE
import media.pixi.rx.firebase.auth.kit.ui.AuthErrorCodes
import media.pixi.rx.firebase.auth.kit.ui.AuthResponse
import media.pixi.rx.firebase.remote.config.ConfigProvider
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashPresenter @Inject constructor(private val navigator: SplashNavigator,
                                          private val authProvider: AuthProvider,
                                          private val configProvider: ConfigProvider): SplashContract.Presenter {

    override fun onLaunch(activity: Activity) {
        if (authProvider.isSignedIn())
            navigator.showHomeScreen(activity)
        else
            navigator.showLoginScreen(activity)

//        configProvider.sync()
//            .subscribe(
//                {
//                    Timber.v("Config Sink Complete") },
//                {
//                    Timber.e(it.message, it) }
//            )
    }

    override fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                navigator.showHomeScreen(activity)
            } else {
                val response = fromResultIntent(data)

                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    navigator.exit(activity)
                    return
                }

                if (response.errorCode == AuthErrorCodes.NO_NETWORK) {
                    // show no network frag
                    return
                }

                // show unknown sign in auth__error
            }
        }
    }

    private fun fromResultIntent(resultIntent: Intent?): AuthResponse? {
        return resultIntent?.getParcelableExtra(AUTH_RESPONSE)
    }
}