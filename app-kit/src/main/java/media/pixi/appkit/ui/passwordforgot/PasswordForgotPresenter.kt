package media.pixi.appkit.ui.passwordforgot

import android.app.Activity
import javax.inject.Inject

class PasswordForgotPresenter @Inject constructor(
    private var signinNavigator: PasswordForgotContract.Navigator): PasswordForgotContract.Presenter {

    override fun takeView(view: PasswordForgotContract.View) {

    }

    override fun dropView() {

    }

    override fun onSendClicked(activity: Activity) {
        signinNavigator.onExit(activity)
    }
}