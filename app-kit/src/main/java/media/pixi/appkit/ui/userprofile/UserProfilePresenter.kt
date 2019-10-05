package media.pixi.appkit.ui.userprofile

import android.app.Activity
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function4
import media.pixi.appkit.data.friends.FriendsProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import timber.log.Timber
import javax.inject.Inject

class UserProfilePresenter @Inject constructor(private var userProfileProvider: UserProfileProvider,
                                               private var friendsProvider: FriendsProvider,
                                               private var userProfileNavigator: UserProfileContract.Navigator): UserProfileContract.Presenter {

    override var userId: String? = null

    data class MyUserProfile(val id: String,
                             val friendCount: Int,
                             val username: String,
                             val firstName: String,
                             val lastName: String,
                             val imageUrl: String,
                             val isFriend: Boolean,
                             val isBlocked: Boolean)

    private var view: UserProfileContract.View? = null
    private var disposables = CompositeDisposable()

    override fun takeView(view: UserProfileContract.View) {
        this.view = view
        view.loading = true

        userId?.let { userId ->

            disposables.add(
                Flowable.zip(
                    userProfileProvider.observerUserProfile(userId),
                    userProfileProvider.isFriend(userId),
                    userProfileProvider.isBlocked(userId),
                    friendsProvider.getFriendsForUser(userId),
                    Function4<UserProfile, Boolean, Boolean, List<String>, MyUserProfile>
                    { userProfile, isFriend, isBlocked, friendCnt ->
                        MyUserProfile(
                            id = userProfile.id,
                            username = userProfile.username,
                            firstName = userProfile.firstName,
                            lastName = userProfile.lastName,
                            imageUrl = userProfile.imageUrl,
                            isFriend = isFriend,
                            isBlocked = isBlocked,
                            friendCount = friendCnt.size) }
                ).subscribe(
                    this::onResult,
                    this::onError
                ))
        }
    }

    override fun dropView() {
        view = null
        disposables.clear()
    }

    override fun onFriendsClicked(activity: Activity) {
        userId?.let { userId ->
            userProfileNavigator.showFriendsScreen(activity, userId)
        }
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

    private fun onResult(userProfile: MyUserProfile) {
        view?.loading = false

        view?.friendCount = userProfile.friendCount
        view?.isFriend = userProfile.isFriend
        view?.profileImageUrl = userProfile.imageUrl
        view?.profileTitle = "${userProfile.firstName} ${userProfile.lastName}"
        view?.profileSubtitle = userProfile.username
    }

    private fun onError(error: Throwable) {
        view?.loading = false

        view?.error = error.message.toString()
        Timber.e(error)
    }

}