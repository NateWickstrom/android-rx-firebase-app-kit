package media.pixi.rx.firebase.auth.kit.ui.signin

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import javax.inject.Inject

class SigninPresenter @Inject constructor(): SigninContract.Presenter {

    lateinit var authProvider: AuthProvider
        @Inject set
    lateinit var signinNavigator: SigninNavigator
        @Inject set

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