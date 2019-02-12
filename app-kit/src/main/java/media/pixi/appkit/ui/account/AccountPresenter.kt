package media.pixi.appkit.ui.account

import android.app.Activity
import android.widget.Toast
import media.pixi.appkit.data.auth.AuthProvider
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var navigator: AccountContract.Navigator): AccountContract.Presenter {

    override fun takeView(view: AccountContract.View) {
        val user = authProvider.getUser()

        view.userImageUrl = user?.imageUrl ?: ""
        view.username = user?.username ?: ""
        view.email = user?.email ?: ""
    }

    override fun dropView() {

    }

    override fun onUserImageClicked(activity: Activity) {
        navigator.showImageFetcher(activity)
    }

    override fun onSignOutClicked(activity: Activity) {
        authProvider.signOut()
        navigator.showSignInScreen(activity)
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