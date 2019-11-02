package media.pixi.appkit.ui.chat.viewholders

import android.view.View
import android.widget.TextView
import media.pixi.appkit.R
import media.pixi.appkit.ui.chat.MessageListItem

class TextMessageViewHolder(itemView: View): MessageViewHolder (itemView) {

    private var messageTextView: TextView = itemView.findViewById(R.id.text_content)

    override fun bind(messageItem: MessageListItem) {
        super.bind(messageItem)
        messageTextView.text = messageItem.message.message
    }

    override fun setOnClickListener(onClickListener: (View) -> Unit) {
        messageTextView.setOnClickListener(onClickListener)
    }
}