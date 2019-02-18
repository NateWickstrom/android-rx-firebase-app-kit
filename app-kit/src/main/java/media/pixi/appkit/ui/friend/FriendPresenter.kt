package media.pixi.appkit.ui.friend

import android.app.Activity
import io.reactivex.disposables.Disposable
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import timber.log.Timber
import javax.inject.Inject

class FriendPresenter @Inject constructor(private var userProfileProvider: UserProfileProvider,
                                          private var friendNavigator: FriendContract.Navigator): FriendContract.Presenter {

    override var userId: String? = null

    private var view: FriendContract.View? = null
    private var disposable: Disposable? = null

    override fun takeView(view: FriendContract.View) {
        this.view = view

        userId?.let {userId ->
            disposable = userProfileProvider.observerUserProfile(userId)
                .subscribe(
                    { onResult(it) },
                    { onError(it) }
                )
        }

    }

    override fun dropView() {
        view = null
        disposable?.dispose()
        disposable = null
    }

    override fun onFriendsClicked(activity: Activity) {
        friendNavigator.showFriendsScreen(activity)
    }

    private fun onResult(userProfile: UserProfile) {
        val name = "${userProfile.firstName} ${userProfile.lastName}"

        view?.profileImageUrl = userProfile.imageUrl
        view?.profileTitle = name
        view?.profileSubtitle = userProfile.username
        view?.friendCount = 100
    }

    private fun onError(error: Throwable) {
        Timber.e(error.message, error)
    }
}