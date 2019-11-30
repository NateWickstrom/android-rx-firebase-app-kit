package media.pixi.appkit.ui.chats

import android.app.Activity
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatsContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean

        fun setResults(results: List<ChatEntity>)
    }

    interface Presenter: BasePresenter<View> {
        fun onNewChatClicked(activity: Activity)

        fun onListItemClicked(activity: Activity, chat: ChatEntity)

        fun onRefresh()
    }

    interface Navigator {
        fun showChatCreator(activity: Activity)
        fun showChat(activity: Activity, chatId: String)
    }
}