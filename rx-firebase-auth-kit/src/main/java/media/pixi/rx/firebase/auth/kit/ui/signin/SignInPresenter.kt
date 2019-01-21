package media.pixi.rx.firebase.auth.kit.ui.signin

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import javax.inject.Inject

class SignInPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var signInNavigator: SignInContract.Navigator): SignInContract.Presenter {

    override fun takeView(veiew: SignInContract.View) {
    }

    override fun dropView() {
    }

    override fun onForgotPasswordClicked(activity: Activity) {
        signInNavigator.showForgotPasswordScreen(activity)
    }

    override fun onSignUpClicked(activity: Activity) {
        signInNavigator.showSignUpScreen(activity)
    }
}