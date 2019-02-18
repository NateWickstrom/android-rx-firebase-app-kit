package media.pixi.appkit.ui.profile

import android.app.Activity
import io.reactivex.disposables.Disposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.auth.AuthUserModel
import timber.log.Timber
import javax.inject.Inject

class ProfilePresenter @Inject constructor(private var authProvider: AuthProvider,
                                           private var navigator: ProfileContract.Navigator): ProfileContract.Presenter {

    private var disposable: Disposable? = null
    private var view: ProfileContract.View? = null

    override fun takeView(view: ProfileContract.View) {
        this.view = view

        disposable?.dispose()
        disposable = authProvider.observerLoggedInUser()
            .subscribe(
                { updateUser(it) },
                { Timber.e(it.message, it) }
            )
    }

    override fun dropView() {
        disposable?.dispose()
        view = null
    }

    override fun onFriendsClicked(activity: Activity) {
        navigator.showFriendsScreen(activity)
    }

    private fun updateUser(user: AuthUserModel) {
        val name = "${user.firstName} ${user.lastName}"

        view?.profileImageUrl = user.imageUrl
        view?.profileTitle = name
        view?.profileSubtitle = user.username
        view?.friendCount = 100
    }
}