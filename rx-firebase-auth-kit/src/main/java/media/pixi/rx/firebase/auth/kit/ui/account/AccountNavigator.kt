package media.pixi.rx.firebase.auth.kit.ui.account

import android.app.Activity
import android.content.Intent
import media.pixi.rx.firebase.auth.kit.ui.passwordupdate.PasswordUpdateActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInActivity
import javax.inject.Inject

class AccountNavigator @Inject constructor(): AccountContract.Navigator {

    override fun showUpdatePasswordScreen(activity: Activity) {
        val intent = Intent(activity, PasswordUpdateActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showSignInScreen(activity: Activity) {
        val intent = Intent(activity, SignInActivity::class.java)
        // set the new task and clear flags
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
    }

}