package media.pixi.appkit.ui.friends

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.domain.GetFriends
import timber.log.Timber
import javax.inject.Inject

class FriendsPresenter @Inject constructor(private var getFriends: GetFriends,
                                           private var authProvider: AuthProvider,
                                           private var navigator: FriendsNavigator): FriendsContract.Presenter {

    override var userId: String? = null

    private var view: FriendsContract.View? = null
    private var disposables = CompositeDisposable()

    private var friends: List<UserProfile>? = null

    override fun takeView(view: FriendsContract.View) {
        this.view = view
        view.loading = true

        userId?.let { userId ->
            disposables.add(getFriends.getFriendsForUser(userId).subscribe(
                { onResult(it) },
                { onError(it) }
            ))
        }
    }

    override fun dropView() {
        view = null
        disposables.clear()
    }

    override fun onListItemClicked(activity: Activity, userProfile: UserProfile) {
        if (authProvider.getUserId() === userProfile.id) {
            navigator.showMyProfilecreen(activity)
        } else {
            navigator.showFriendScreen(activity, userProfile.id)
        }
    }

    private fun onComplete() {
        view?.loading = false
        view?.showNoResults(friends?.isEmpty() ?: true)
    }

    private fun onResult(friends: List<UserProfile>) {
        view?.loading = false
        view?.setResults(friends)
        view?.showNoResults(friends.isEmpty())
    }

    private fun onError(error: Throwable) {
        view?.loading = false
        view?.error = error.message.toString()
        view?.showNoResults(true)
        Timber.e(error)
    }

}