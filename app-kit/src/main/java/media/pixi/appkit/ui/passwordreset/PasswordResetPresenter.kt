package media.pixi.appkit.ui.passwordreset

import android.app.Activity
import javax.inject.Inject

class PasswordResetPresenter @Inject constructor(
    private val navigator: PasswordResetContract.Navigator) : PasswordResetContract.Presenter {

    override fun takeView(view: PasswordResetContract.View) {

    }

    override fun dropView() {

    }

    override fun onOpenEmailClicked(activity: Activity) {
        navigator.showEmail(activity)
    }

}
