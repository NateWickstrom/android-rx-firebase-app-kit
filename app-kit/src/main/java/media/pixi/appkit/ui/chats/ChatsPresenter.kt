package media.pixi.appkit.ui.chats

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.domain.chats.ChatListItemsGetter
import javax.inject.Inject

class ChatsPresenter @Inject constructor(private val navigator: ChatsNavigator,
                                         private val getChats: ChatListItemsGetter) : ChatsContract.Presenter {

    private var view: ChatsContract.View? = null

    private var disposables = CompositeDisposable()

    override fun takeView(view: ChatsContract.View) {
        this.view = view
        view.loading = true

        disposables.add(getChats.getChats().subscribe(
            { onResult(it) },
            { onError(it) }
        ))
    }

    override fun dropView() {
        disposables.clear()
        view = null
    }

    override fun onNewChatClicked(activity: Activity) {
        navigator.showChatCreator(activity)
    }

    override fun onListItemClicked(activity: Activity, chat: ChatEntity) {
        navigator.showChat(activity, chat.id)
    }

    override fun onRefresh() {
        view?.loading = true
        disposables.clear()
        disposables.add(getChats.getChats().subscribe(
            { onResult(it) },
            { onError(it) }
        ))
    }

    private fun onResult(results: List<ChatEntity>) {
        view?.loading = false
        view?.setResults(results)
    }

    private fun onError(error: Throwable) {
        view?.loading = false
    }

}