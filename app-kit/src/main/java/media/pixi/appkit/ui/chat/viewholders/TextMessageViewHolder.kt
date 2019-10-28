package media.pixi.appkit.ui.chat.viewholders

import android.view.View
import media.pixi.appkit.ui.chat.MessageListItem

class TextMessageViewHolder(itemView: View): MessageViewHolder (itemView) {

    override fun bind(messageItem: MessageListItem) {
        super.bind(messageItem)
        messageTextView.text = messageItem.message.message
        setBubbleHidden(false)
        setTextHidden(false)
    }
}