package media.pixi.appkit.ui.chat

import android.app.Activity
import media.pixi.appkit.domain.chats.models.MessageListItem
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean
        var canSend: Boolean
        var title: String

        fun setResults(results: List<MessageListItem>)
        fun scrollToEnd()

        fun showTextSpeedDial(messageListItem: MessageListItem)
        fun showImageSpeedDial(messageListItem: MessageListItem)
        fun showLocationSpeedDial(messageListItem: MessageListItem)

    }

    interface Presenter: BasePresenter<View> {
        fun send(text: String)

        fun onTextClicked(position: Int, item: MessageListItem)

        fun onImageClicked(position: Int, item: MessageListItem)

        fun onLocationClicked(position: Int, item: MessageListItem)

        fun onOptionsClicked(activity: Activity)

        fun onShowChatMembersClicked(activity: Activity)

        fun onItemsViewed(firstPosition: Int, lastPosition: Int)
    }

    interface Navigator {

        fun showOptions(activity: Activity)

        fun showImage(activity: Activity)

        fun showChatMembers(activity: Activity, chatId: String)
    }
}