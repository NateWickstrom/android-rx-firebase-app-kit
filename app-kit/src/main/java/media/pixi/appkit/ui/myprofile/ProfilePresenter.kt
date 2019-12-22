package media.pixi.appkit.ui.myprofile

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.auth.AuthUserModel
import media.pixi.appkit.data.friends.FriendsProvider
import timber.log.Timber
import javax.inject.Inject

class ProfilePresenter @Inject constructor(private var authProvider: AuthProvider,
                                           private var friendsProvider: FriendsProvider,
                                           private var navigator: ProfileContract.Navigator): ProfileContract.Presenter {

    private var disposables = CompositeDisposable()
    private var view: ProfileContract.View? = null

    override fun takeView(view: ProfileContract.View) {
        this.view = view

        disposables.add(authProvider.observerLoggedInUser()
            .subscribe(
                { updateUser(it) },
                { Timber.e(it.message, it) }
            ))

        disposables.add(friendsProvider.getFriendsForUser(authProvider.getUserId()!!)
            .subscribe(
                { updateFriendCount(it.size) },
                { onError(it) }
            ))
    }

    override fun dropView() {
        disposables.clear()
        view = null
    }

    override fun onFriendsClicked(activity: Activity) {
        authProvider.getUserId()?.let { userId ->
            navigator.showFriendsScreen(activity, userId)
        }
    }

    private fun updateFriendCount(friendCount: Int) {
        view?.friendCount = friendCount
    }

    private fun updateUser(user: AuthUserModel) {
        val name = "${user.firstName} ${user.lastName}"

        view?.profileImageUrl = user.imageUrl
        view?.profileTitle = name
        view?.profileSubtitle = user.username
    }

    private fun onError(error: Throwable) {
        Timber.e(error.message, error)
    }
}