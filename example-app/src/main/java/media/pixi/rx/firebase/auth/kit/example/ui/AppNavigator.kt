package media.pixi.rx.firebase.auth.kit.example.ui

import android.app.Activity
import android.content.Intent
import media.pixi.rx.firebase.auth.kit.ui.account.AccountActivity
import media.pixi.rx.firebase.auth.kit.ui.account.AccountContract
import media.pixi.rx.firebase.auth.kit.ui.passwordupdate.PasswordUpdateActivity
import media.pixi.rx.firebase.profile.kit.ui.profile.ProfileContract
import javax.inject.Inject

class AppNavigator @Inject constructor(): ProfileContract.Navigator, AccountContract.Navigator {

    override fun showUpdatePasswordScreen(activity: Activity) {
        val intent = Intent(activity, PasswordUpdateActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showSignInScreen(activity: Activity) {
        val intent = Intent(activity, SplashActivity::class.java)
        // set the new task and clear flags
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
    }

    override fun showAccountScreen(activity: Activity) {
        val intent = Intent(activity, AccountActivity::class.java)
        activity.startActivity(intent)
    }

}