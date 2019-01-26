package media.pixi.rx.firebase.auth.kit.ui.account

import android.app.Activity
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var navigator: AccountContract.Navigator): AccountContract.Presenter {

    override fun takeView(view: AccountContract.View) {

    }

    override fun dropView() {

    }

    override fun onSignOutClicked(activity: Activity) {
        authProvider.signOut()
        //navigator.onExit(auth__activity)
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