package media.pixi.appkit.ui.passwordforgot

import android.app.Activity
import javax.inject.Inject

class PasswordForgotNavigator @Inject constructor(): PasswordForgotContract.Navigator {

    override fun onExit(activity: Activity) {
        activity.finish()
    }

}
