package media.pixi.appkit.ui.friend

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import timber.log.Timber
import javax.inject.Inject

class FriendPresenter @Inject constructor(private var userProfileProvider: UserProfileProvider,
                                          private var friendNavigator: FriendContract.Navigator): FriendContract.Presenter {

    override var userId: String? = null

    private var view: FriendContract.View? = null
    private var disposables = CompositeDisposable()

    override fun takeView(view: FriendContract.View) {
        this.view = view

        userId?.let { userId ->

            disposables.add(userProfileProvider.observerUserProfile(userId).subscribe(
                { onResult(it) },
                { onError(it) }
            ))

            disposables.add(userProfileProvider.isBlocked(userId).subscribe(
                {
                    this.view?.isFriend = it
                },
                {
                    onError(it)
                }
            ))

            disposables.add(userProfileProvider.isFriend(userId).subscribe(
                {
                    //TODO
                },
                {
                    onError(it)
                }
            ))
        }
    }

    override fun dropView() {
        view = null
        disposables.clear()
    }

    override fun onFriendsClicked(activity: Activity) {
        friendNavigator.showFriendsScreen(activity)
    }

    override fun onUnFriendsClicked(activity: Activity) {
        userId?.let { userId ->
            view?.isFriend = false
            disposables.add(userProfileProvider.unFriend(userId)
                .subscribe(
                    {
                        //onResult(it)
                    },
                    {
                        view?.isFriend = true
                        Timber.e(it.message, it)
                    }
                ))
        }
    }

    override fun onAddFriendClicked(activity: Activity) {
        userId?.let { userId ->
            view?.isFriend = true
            disposables.add(userProfileProvider.addFriend(userId)
                .subscribe(
                    {
                        //onResult(it)
                    },
                    {
                        view?.isFriend = false
                        Timber.e(it.message, it)
                    }
                ))
        }
    }

    private fun onResult(userProfile: UserProfile) {
        //val userProfile = model.userProfile

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