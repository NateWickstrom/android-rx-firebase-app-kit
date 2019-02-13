package media.pixi.appkit.ui.account

import android.app.Activity
import io.reactivex.disposables.Disposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import timber.log.Timber
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var userProfileProvider: UserProfileProvider,
    private var navigator: AccountContract.Navigator): AccountContract.Presenter {

    private var disposable: Disposable? = null
    private var view: AccountContract.View? = null


    override fun takeView(view: AccountContract.View) {
        this.view = view
        val user = authProvider.getUser()

        view.email = user?.email ?: ""

        disposable = userProfileProvider.observerCurrentUserProfile()
            .subscribe(
                { onResult(it) },
                { onError(it) }
            )
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

    private fun onResult(userProfile: UserProfile) {
        view?.username = userProfile.username
        view?.firstName = userProfile.firstName
        view?.lastName = userProfile.lastName
        view?.userImageUrl = userProfile.imageUrl
    }

    private fun onError(error: Throwable) {
        Timber.e(error.message, error)
    }
}