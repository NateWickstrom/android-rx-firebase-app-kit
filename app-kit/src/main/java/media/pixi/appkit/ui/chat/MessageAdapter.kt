package media.pixi.appkit.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import media.pixi.appkit.R
import media.pixi.appkit.ui.chat.viewholders.ImageMessageViewHolder
import media.pixi.appkit.ui.chat.viewholders.LocationMessageViewHolder
import media.pixi.appkit.ui.chat.viewholders.MessageViewHolder
import media.pixi.appkit.ui.chat.viewholders.TextMessageViewHolder
import timber.log.Timber


class MessageAdapter(private val presenter: ChatContract.Presenter): RecyclerView.Adapter<MessageViewHolder>() {

    private val items = mutableListOf<MessageListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view  = when (viewType) {
            MessageViewHolderType.MY_TEXT.id ->
                inflater.inflate(R.layout.view_message_text_me, parent, false)
            MessageViewHolderType.MY_IMAGE.id,
            MessageViewHolderType.MY_LOCATION.id ->
                inflater.inflate(R.layout.view_message_me, parent, false)

            MessageViewHolderType.THEIR_TEXT.id ->
                inflater.inflate(R.layout.view_message_text_reply, parent, false)
            MessageViewHolderType.THEIR_IMAGE.id,
            MessageViewHolderType.THEIR_LOCATION.id ->
                inflater.inflate(R.layout.view_message_reply, parent, false)

            else -> throw IllegalArgumentException("Unknown view type")
        }

        return when (viewType) {
            MessageViewHolderType.MY_TEXT.id,
            MessageViewHolderType.THEIR_TEXT.id -> TextMessageViewHolder(view)
            MessageViewHolderType.MY_IMAGE.id,
            MessageViewHolderType.THEIR_IMAGE.id -> ImageMessageViewHolder(view)
            MessageViewHolderType.MY_LOCATION.id,
            MessageViewHolderType.THEIR_LOCATION.id -> LocationMessageViewHolder(view)

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        when (holder) {
            is TextMessageViewHolder ->
                holder.itemView.setOnClickListener { presenter.onTextClicked(position, item) }
            is ImageMessageViewHolder ->
                holder.itemView.setOnClickListener { presenter.onImageClicked(position, item) }
            is LocationMessageViewHolder ->
                holder.itemView.setOnClickListener { presenter.onLocationClicked(position, item) }
            else ->
                Timber.w(TAG, "unhandled click event for: ${holder.javaClass.simpleName}")
        }
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

    companion object {
        const val TAG = "MessageAdapter"
    }

}