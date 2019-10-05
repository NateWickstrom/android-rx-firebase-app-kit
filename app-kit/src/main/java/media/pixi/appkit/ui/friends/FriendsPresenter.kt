package media.pixi.appkit.ui.friends

import android.app.Activity
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.domain.GetFriends
import javax.inject.Inject

class FriendsPresenter @Inject constructor(private var getFriends: GetFriends,
                                           private var navigator: FriendsNavigator): FriendsContract.Presenter {

    override var userId: String? = null

    private var view: FriendsContract.View? = null
    private var disposables = CompositeDisposable()

    override fun takeView(view: FriendsContract.View) {
        this.view = view

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
        navigator.showFriendScreen(activity, userProfile.id)
    }

    private fun onResult(friends: List<UserProfile>) {
        view?.let {
            it.setResults(friends)
        }
    }

    private fun onError(error: Throwable) {
        Log.d("FriendsActivity", error.toString())
    }

}