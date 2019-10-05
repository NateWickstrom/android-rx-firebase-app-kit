package media.pixi.appkit.ui.settings

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.ui.account.AccountActivity
import media.pixi.appkit.ui.develop.DevelopActivity
import javax.inject.Inject

class SettingsNavigator @Inject constructor(): SettingsContract.Navigator {

    override fun showAccount(activity: Activity) {
        val intent = Intent(activity, AccountActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showDevelop(activity: Activity) {
        val intent = Intent(activity, DevelopActivity::class.java)
        activity.startActivity(intent)
    }
}