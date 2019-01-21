package media.pixi.rx.firebase.auth.kit.ui.signup

import android.app.Activity
import android.content.Intent
import javax.inject.Inject

class SignUpNavigator @Inject constructor(): SignUpContract.Navigator {

    override fun onLoggedInSuccessfully(activity: Activity) {
        val data = Intent()
        activity.setResult(Activity.RESULT_OK, data)
        activity.finish()
    }
}