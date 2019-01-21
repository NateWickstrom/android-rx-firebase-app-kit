package media.pixi.rx.firebase.auth.kit.ui.account

import android.app.Activity
import android.content.Intent
import media.pixi.rx.firebase.auth.kit.ui.passwordupdate.PasswordUpdateActivity
import javax.inject.Inject

class AccountNavigator @Inject constructor(): AccountContract.Navigator {

    override fun onExit(activity: Activity) {
        activity.finish()
    }

    override fun showUpdatePasswordScreen(activity: Activity) {
        val intent = Intent(activity, PasswordUpdateActivity::class.java)
        activity.startActivity(intent)
    }

}