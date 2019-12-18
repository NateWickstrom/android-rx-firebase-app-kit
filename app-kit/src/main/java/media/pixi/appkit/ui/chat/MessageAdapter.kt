package media.pixi.appkit.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import media.pixi.appkit.R
import java.util.concurrent.TimeUnit


class MessageAdapter(private val onMessageListItemClicked: OnMessageListItemClicked?): RecyclerView.Adapter<MessageViewHolder>() {

    interface OnMessageListItemClicked {
        fun onMessageListItemClicked(position: Int, item: MessageListItem)
    }

    val items = mutableListOf<MessageListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view  = when (viewType) {
            MessageViewHolderType.MY_TEXT.id ->
                inflater.inflate(R.layout.view_message_text_me, parent, false)
//            MessageViewHolderType.MY_IMAGE.id,
//            MessageViewHolderType.MY_LOCATION.id ->
//                inflater.inflate(R.layout.view_message_image_me, parent, false)

            MessageViewHolderType.THEIR_TEXT.id ->
                inflater.inflate(R.layout.view_message_text_reply, parent, false)
//            MessageViewHolderType.THEIR_IMAGE.id,
//            MessageViewHolderType.THEIR_LOCATION.id ->
//                inflater.inflate(R.layout.view_message_image_reply, parent, false)

            else -> throw IllegalArgumentException("Unknown view type")
        }

        return when (viewType) {
            MessageViewHolderType.MY_TEXT.id,
            MessageViewHolderType.THEIR_TEXT.id -> MessageViewHolder(view)
//            MessageViewHolderType.MY_IMAGE.id,
//            MessageViewHolderType.THEIR_IMAGE.id,
//            MessageViewHolderType.MY_LOCATION.id,
//            MessageViewHolderType.THEIR_LOCATION.id -> ImageMessageViewHolder(view)

            else -> throw IllegalArgumentException("Unknown view type")
        }
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
        return items[position].messageViewHolderType.id
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
        const val TAG = "MessageAdapter"
    }

}