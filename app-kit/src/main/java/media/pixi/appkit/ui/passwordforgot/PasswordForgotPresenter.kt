package media.pixi.appkit.ui.passwordforgot

import android.app.Activity
import media.pixi.appkit.data.auth.AuthProvider
import timber.log.Timber
import javax.inject.Inject

class PasswordForgotPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var signinNavigator: PasswordForgotContract.Navigator): PasswordForgotContract.Presenter {

    var view: PasswordForgotContract.View? = null

    override fun takeView(view: PasswordForgotContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
    }

    override fun onSendClicked(activity: Activity) {
        signinNavigator.onExit(activity)

        view?.email?.let {
            view?.loading = true
            authProvider.sendPasswordResetEmail(it).subscribe(
                { onEmailSent(activity) },
                { onError(it) }
            )
        }
    }

    private fun onEmailSent(activity: Activity) {
        view?.loading = false
        signinNavigator.onExit(activity)
    }

    private fun onError(error: Throwable) {
        view?.loading = false
        Timber.e(error.message, error)
    }
}