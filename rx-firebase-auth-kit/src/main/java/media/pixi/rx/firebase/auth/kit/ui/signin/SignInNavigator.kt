package media.pixi.rx.firebase.auth.kit.ui.signin

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import media.pixi.rx.firebase.auth.kit.ui.passwordforgot.PasswordForgotActivity
import media.pixi.rx.firebase.auth.kit.ui.signup.SignUpActivity
import javax.inject.Inject

class SignInNavigator @Inject constructor(): SignInContract.Navigator {

    override fun onExit(activity: Activity) {
        val data = Intent()
        activity.setResult(RESULT_OK, data)
        activity.finish()
    }

    override fun showForgotPasswordScreen(activity: Activity) {
        val intent = Intent(activity, PasswordForgotActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showSignUpScreen(activity: Activity) {
        val intent = Intent(activity, SignUpActivity::class.java)
        activity.startActivity(intent)
    }
}