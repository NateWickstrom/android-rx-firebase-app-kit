package media.pixi.appkit.ui.chats

import android.app.Activity
import media.pixi.appkit.domain.chats.Chat
import javax.inject.Inject

class ChatsPresenter @Inject constructor(val navigator: ChatsNavigator) : ChatsContract.Presenter {

    private var view: ChatsContract.View? = null

    override fun takeView(view: ChatsContract.View) {
        this.view = view
        view.loading = true
        loadFakeChats()
    }

    override fun dropView() {
        view = null
    }

    override fun onNewChatClicked(activity: Activity) {
        navigator.showChatCreator(activity)
    }

    override fun onListItemClicked(activity: Activity, chat: Chat) {
        navigator.showChat(activity, chat.id)
    }

    private fun onResult(results: List<Chat>) {
        view?.loading = false
        view?.setResults(results)
    }

    private fun onError(error: Throwable) {
        view?.loading = false
    }

    private fun loadFakeChats() {
        val chats = mutableListOf(Chat(id = "some_id", title = "Chat Title", subtitle = "subtitle"))
        onResult(chats)
    }
}