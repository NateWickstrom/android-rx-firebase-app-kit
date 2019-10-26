package media.pixi.appkit.ui.chats

import android.app.Activity
import media.pixi.appkit.domain.chats.Chat
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatsContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean

        fun setResults(results: List<Chat>)
    }

    interface Presenter: BasePresenter<View> {
        fun onNewChatClicked(activity: Activity)

        fun onListItemClicked(activity: Activity, chat: Chat)
    }

    interface Navigator {
        fun showChatCreator(activity: Activity)
        fun showChat(activity: Activity, chatId: String)
    }
}