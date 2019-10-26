package media.pixi.appkit.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.appkit__item_chat.view.*
import media.pixi.appkit.R
import media.pixi.appkit.domain.chats.Chat
import media.pixi.appkit.utils.ImageUtils

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    var onClickListener: ((Chat) -> Unit)? = null
    var onLongClickListener: ((Int) -> Boolean)? = null

    private val items = mutableListOf<Chat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.appkit__item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onClickListener?.invoke(item) }
        holder.itemView.setOnLongClickListener { onLongClickListener?.invoke(position) ?: false}
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.hashCode().toLong()
    }

    fun set(results: List<Chat>) {
        items.clear()
        items.addAll(results)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: Chat) = with(itemView) {
            //ImageUtils.setUserImage(item_image, item.item_image)
            title.text = item.title
            subtitle.text = item.subtitle
        }
    }
}