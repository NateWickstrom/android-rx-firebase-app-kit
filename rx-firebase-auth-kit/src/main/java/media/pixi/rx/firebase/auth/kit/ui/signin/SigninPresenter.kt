package media.pixi.rx.firebase.auth.kit.ui.signin

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import javax.inject.Inject

class SigninPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var signinNavigator: SigninContract.Navigator): SigninContract.Presenter {

    override fun takeView(veiew: SigninContract.View) {
    }

    override fun dropView() {
    }

    override fun onForgotPasswordClicked(activity: Activity) {
        signinNavigator.showForgotPasswordScreen(activity)
    }

    override fun onSignUpClicked(activity: Activity) {
        signinNavigator.showSignUpScreen(activity)
    }
}