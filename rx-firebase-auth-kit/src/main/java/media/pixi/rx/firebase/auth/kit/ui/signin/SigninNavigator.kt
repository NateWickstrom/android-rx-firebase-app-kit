package media.pixi.rx.firebase.auth.kit.ui.signin

import android.app.Activity
import android.content.Intent
import media.pixi.rx.firebase.auth.kit.ui.passwordforgot.PasswordForgotActivity
import media.pixi.rx.firebase.auth.kit.ui.signup.SignupActivity
import javax.inject.Inject

class SigninNavigator @Inject constructor(): SigninContract.Navigator {

    override fun showForgotPasswordScreen(activity: Activity) {
        val intent = Intent(activity, PasswordForgotActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showSignUpScreen(activity: Activity) {
        val intent = Intent(activity, SignupActivity::class.java)
        activity.startActivity(intent)
    }
}