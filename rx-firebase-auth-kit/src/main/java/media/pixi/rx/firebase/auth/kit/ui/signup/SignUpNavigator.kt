package media.pixi.rx.firebase.auth.kit.ui.signup

import android.app.Activity
import javax.inject.Inject

class SignUpNavigator @Inject constructor(): SignUpContract.Navigator {

    override fun onExit(activity: Activity) {
        activity.finish()
    }
}