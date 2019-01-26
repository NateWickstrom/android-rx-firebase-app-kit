package media.pixi.rx.firebase.auth.kit.example.ui

import android.app.Activity
import android.content.Intent
import media.pixi.rx.firebase.auth.kit.ui.account.AccountActivity
import media.pixi.rx.firebase.profile.kit.ui.profile.ProfileContract
import javax.inject.Inject

class AppNavigator @Inject constructor(): ProfileContract.Navigator {

    override fun showAccountScreen(activity: Activity) {
        val intent = Intent(activity, AccountActivity::class.java)
        activity.startActivity(intent)
    }

}