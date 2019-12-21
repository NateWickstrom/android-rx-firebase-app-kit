package media.pixi.appkit.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import media.pixi.appkit.R
import media.pixi.appkit.domain.chats.models.MessageListItem
import java.util.concurrent.TimeUnit


class MessageAdapter(private val onMessageListItemClicked: OnMessageListItemClicked?): RecyclerView.Adapter<MessageViewHolder>() {

    interface OnMessageListItemClicked {
        fun onMessageListItemClicked(position: Int, item: MessageListItem)
    }

    val items = mutableListOf<MessageListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutId = when (viewType) {
            VIEW_TYPE_ME -> R.layout.view_message_text_me
            VIEW_TYPE_REPLY -> R.layout.view_message_text_reply
            else -> throw IllegalArgumentException("Unknown view type")
        }
        val view = inflater.inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, showTimestamp(position))
        holder.setOnClickListener { onMessageListItemClicked?.onMessageListItemClicked(position, item) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isMe) VIEW_TYPE_ME else VIEW_TYPE_REPLY
    }

    override fun getItemId(position: Int): Long {
        return items[position].message.id.hashCode().toLong()
    }

    fun set(results: List<MessageListItem>) {
        items.clear()
        items.addAll(results)
        notifyDataSetChanged()
    }

    private fun showTimestamp(position: Int): Boolean {
        if (position == 0) return true
        val previous = items[position - 1]
        val current = items[position]

        val timeDiff = TimeUnit.MILLISECONDS.toMinutes(current.timeInMillis) -
                TimeUnit.MILLISECONDS.toMinutes(previous.timeInMillis)

        return timeDiff >= TIME_DIFF
    }

    companion object {
        const val TIME_DIFF = 5
        const val VIEW_TYPE_ME = 0
        const val VIEW_TYPE_REPLY = 1
    }

}