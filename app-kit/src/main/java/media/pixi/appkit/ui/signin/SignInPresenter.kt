package media.pixi.appkit.ui.signin

import android.app.Activity
import io.reactivex.disposables.Disposable
import media.pixi.appkit.data.auth.AuthProvider
import javax.inject.Inject

class SignInPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var signInNavigator: SignInContract.Navigator): SignInContract.Presenter {

    private var view: SignInContract.View? = null

    private var email: String = ""
    private var password: String = ""

    private var disposable: Disposable? = null

    override fun takeView(view: SignInContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
        disposable?.dispose()
    }

    override fun onEmailTextChanged(email: String) {
        this.email = email
    }

    override fun onPasswordTextChanged(password: String) {
        this.password = password
    }

    override fun onSignInClicked(activity: Activity) {
        view?.error = ""
        disposable?.dispose()
        view?.loading = true
        disposable = authProvider.signIn(email, password)
            .subscribe(
                { onSignedIn(activity) },
                { onError(it) }
            )
    }

    override fun onForgotPasswordClicked(activity: Activity) {
        signInNavigator.showForgotPasswordScreen(activity)
    }

    override fun onSignUpClicked(activity: Activity) {
        signInNavigator.showSignUpScreen(activity)
    }

    private fun onSignedIn(activity: Activity) {
        view?.loading = false
        signInNavigator.onLoggedInSuccessfully(activity)
    }

    private fun onError(error: Throwable) {
        view?.loading = false
        view?.error = error.message ?: "Unknown error occurred"
    }
}