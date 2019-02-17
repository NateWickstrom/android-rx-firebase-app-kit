package media.pixi.appkit.ui.friend

import io.reactivex.disposables.Disposable
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import timber.log.Timber
import javax.inject.Inject

class FriendPresenter @Inject constructor(private var userProfileProvider: UserProfileProvider): FriendContract.Presenter {

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