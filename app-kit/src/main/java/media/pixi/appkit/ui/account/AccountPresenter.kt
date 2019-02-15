package media.pixi.appkit.ui.account

import android.app.Activity
import io.reactivex.disposables.Disposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.auth.AuthUserModel
import timber.log.Timber
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var navigator: AccountContract.Navigator): AccountContract.Presenter {

    private var disposable: Disposable? = null
    private var view: AccountContract.View? = null

    private var originalUser: AuthUserModel? = null

    override fun takeView(view: AccountContract.View) {
        this.view = view

        disposable = authProvider.observerLoggedInUser()
            .subscribe(
                { onResult(it) },
                { onError(it) }
            )
    }

    override fun dropView() {
        disposable?.dispose()
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

    private fun onResult(user: AuthUserModel) {
        originalUser = user
        view?.email = user.email
        view?.username = user.username
        view?.firstName = user.firstName
        view?.lastName = user.lastName
        view?.userImageUrl = user.imageUrl
    }

    private fun onError(error: Throwable) {
        Timber.e(error.message, error)
    }
}