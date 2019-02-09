package media.pixi.appkit.ui.passwordupdate

import android.app.Activity
import javax.inject.Inject

class PasswordUpdateNavigator @Inject constructor(): PasswordUpdateContract.Navigator {

    override fun onExit(activity: Activity) {
        activity.finish()
    }

}