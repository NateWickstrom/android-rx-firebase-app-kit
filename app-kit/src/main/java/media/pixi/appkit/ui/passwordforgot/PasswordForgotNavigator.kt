package media.pixi.appkit.ui.passwordforgot

import android.app.Activity
import media.pixi.appkit.ui.passwordreset.PasswordResetActivity
import javax.inject.Inject

class PasswordForgotNavigator @Inject constructor(): PasswordForgotContract.Navigator {

    override fun onExit(activity: Activity, email: String) {
        PasswordResetActivity.lauch(activity, email)
        activity.finish()
    }

}
