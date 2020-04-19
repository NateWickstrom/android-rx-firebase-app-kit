package media.pixi.appkit.ui.chat

import android.app.Activity
import media.pixi.appkit.domain.chats.models.MessageAttachment
import media.pixi.appkit.domain.chats.models.MessageListItem
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean
        var canSend: Boolean
        var title: String
        var text: String

        fun setResults(results: List<MessageListItem>)
        fun scrollToEnd()

        fun addAttachment(attachment: MessageAttachment)
        fun addAttachments(attachments: List<MessageAttachment>)
        fun clearAttachments()

        fun showTextSpeedDial(messageListItem: MessageListItem)
        fun showImageSpeedDial(messageListItem: MessageListItem)
        fun showLocationSpeedDial(messageListItem: MessageListItem)

    }

    interface Presenter: BasePresenter<View> {
        fun send(text: String, attachments: List<MessageAttachment>)

        fun saveDraft(text: String, attachments: List<MessageAttachment>)

        fun onMessageListItemClicked(activity: Activity, item: MessageListItem, position: Int)

        fun onTextClicked(position: Int, item: MessageListItem)

        fun onImageClicked(position: Int, item: MessageListItem)

        fun onLocationClicked(position: Int, item: MessageListItem)

        fun onOptionsClicked(activity: Activity)

        fun onAttachmentClicked(activity: Activity, attachment: MessageAttachment)

        fun onAttachmentDeleteClicked(position: Int, attachment: MessageAttachment)

        fun onShowChatMembersClicked(activity: Activity)

        fun onItemsViewed(firstPosition: Int, lastPosition: Int)

        fun onImageSelected(path: String)

        fun onVideoSelected(path: String)
    }

    interface Navigator {

        fun showOptions(activity: Activity)

        fun showImage(activity: Activity, url: String)

        fun showLocalVideo(activity: Activity, url: String)

        fun showRemoteVideo(activity: Activity, url: String)

        fun showChatMembers(activity: Activity, chatId: String)
    }
}