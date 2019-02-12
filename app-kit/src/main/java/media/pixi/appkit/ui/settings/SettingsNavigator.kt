package media.pixi.appkit.ui.settings

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.ui.account.AccountActivity
import javax.inject.Inject

class SettingsNavigator @Inject constructor(): SettingsContract.Navigator {

    override fun showAccount(activity: Activity) {
        val intent = Intent(activity, AccountActivity::class.java)
        activity.startActivity(intent)
    }

}