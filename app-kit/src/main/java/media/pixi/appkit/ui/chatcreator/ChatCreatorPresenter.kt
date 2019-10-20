package media.pixi.appkit.ui.chatcreator

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.domain.GetFriends
import timber.log.Timber
import javax.inject.Inject

class ChatCreatorPresenter @Inject constructor(private var getFriends: GetFriends): ChatCreatorContract.Presenter {

    private var view: ChatCreatorContract.View? = null
    private var disposables = CompositeDisposable()

    override fun takeView(view: ChatCreatorContract.View) {
        this.view = view

        view.loading = true

        disposables.add(getFriends.getFriends().subscribe(
            { onResult(it) },
            { onError(it) }
        ))
    }

    override fun dropView() {
        disposables.clear()
        view = null
    }

    override fun onListItemClicked(activity: Activity, userProfile: UserProfile) {

    }

    override fun onStartChatClicked(activity: Activity) {

    }

    override fun onCreateChatClicked(activity: Activity) {

    }

    override fun onTextChanged(query: String) {

    }

    private fun onResult(friends: List<UserProfile>) {
        view?.loading = false
        view?.setContacts(friends)
    }

    private fun onError(error: Throwable) {
        view?.loading = false
        view?.error = error.message.toString()
        Timber.e(error)
    }
}