package media.pixi.rx.firebase.auth.kit.ui.account

import android.app.Activity
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private var navigator: AccountContract.Navigator): AccountContract.Presenter {

    override fun takeView(view: AccountContract.View?) {

    }

    override fun dropView() {

    }

    override fun onSaveClicked(activity: Activity) {

    }

    override fun onResetClicked(activity: Activity) {

    }

    override fun onUpdatePasswordClicked(activity: Activity) {
        navigator.showUpdatePasswordScreen(activity)
    }

    override fun onVerifyEmailClicked(activity: Activity) {

    }
}