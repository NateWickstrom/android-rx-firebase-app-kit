package media.pixi.appkit.ui.passwordreset

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.R
import javax.inject.Inject


class PasswordResetNavigator @Inject internal constructor(): PasswordResetContract.Navigator {

    override fun showEmail(activity: Activity) {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.open_email)))
    }

}
