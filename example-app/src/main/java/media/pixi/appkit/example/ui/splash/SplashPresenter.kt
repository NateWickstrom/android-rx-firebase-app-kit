package media.pixi.appkit.example.ui.splash

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.config.ConfigProvider
import media.pixi.appkit.example.ui.splash.SplashContract.Companion.REQUEST_CODE
import media.pixi.appkit.ui.AUTH_RESPONSE
import media.pixi.appkit.ui.AuthErrorCodes
import media.pixi.appkit.ui.AuthResponse

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashPresenter @Inject constructor(private val navigator: SplashNavigator,
                                          private val authProvider: AuthProvider,
                                          private val configProvider: ConfigProvider
):
    SplashContract.Presenter {

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

                // show unknown sign in appkit__error
            }
        }
    }

    private fun fromResultIntent(resultIntent: Intent?): AuthResponse? {
        return resultIntent?.getParcelableExtra(AUTH_RESPONSE)
    }
}