package media.pixi.appkit.ui.signin

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import media.pixi.appkit.ui.passwordforgot.PasswordForgotActivity
import media.pixi.appkit.ui.signup.SignUpActivity
import javax.inject.Inject

class SignInNavigator @Inject constructor(): SignInContract.Navigator {

    override fun onLoggedInSuccessfully(activity: Activity) {
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
        activity.startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SignInNavigator.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                onLoggedInSuccessfully(activity)
            } else {
                // User pressed back button/home
            }
        }
    }

    companion object {
        const val REQUEST_CODE = 99
    }
}