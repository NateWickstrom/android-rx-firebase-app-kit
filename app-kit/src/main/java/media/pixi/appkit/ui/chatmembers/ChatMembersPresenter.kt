package media.pixi.appkit.ui.chatmembers

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.domain.chats.ChatMembersGetter
import timber.log.Timber
import javax.inject.Inject

class ChatMembersPresenter @Inject constructor(private var getChats: ChatMembersGetter,
                                               private var authProvider: AuthProvider,
                                               private var navigator: ChatMembersNavigator): ChatMembersContract.Presenter {

    override var chatId: String? = null

    private var view: ChatMembersContract.View? = null
    private var disposables = CompositeDisposable()

    private var friends: List<UserProfile>? = null

    override fun takeView(view: ChatMembersContract.View) {
        this.view = view
        view.loading = true

        chatId?.let { chatId ->
            disposables.add(getChats.getChatMembers(chatId).subscribe(
                { onResult(it) },
                { onError(it) },
                { onComplete() }
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
            navigator.showUserProfileScreen(activity, userProfile.id)
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