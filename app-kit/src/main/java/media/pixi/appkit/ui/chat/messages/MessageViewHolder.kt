package media.pixi.appkit.ui.chat.messages

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import media.pixi.appkit.domain.chats.models.MessageListItem

abstract class MessageViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    abstract fun bind(messageItem: MessageListItem, showTimeStamp: Boolean)

    abstract fun setOnClickListener(onClickListener: (View) -> Unit)

}