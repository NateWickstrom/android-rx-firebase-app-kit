package media.pixi.appkit.ui.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.domain.chats.GetChats
import java.lang.ref.WeakReference

class ChatAdapter(private val chats: GetChats): RecyclerView.Adapter<ChatViewHolder>() {

    var onClickListener: ((ChatEntity) -> Unit)? = null
    var onLongClickListener: ((Int) -> Boolean)? = null

    private val items = mutableListOf<ChatEntity>()
    private val holders = mutableSetOf<WeakReference<ChatViewHolder>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(ChatViewHolder.LAYOUT_ID, parent, false)
        val holder = ChatViewHolder(view, chats)
        holders.add(WeakReference(holder))
        return holder
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
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

    fun set(results: List<ChatEntity>) {
        items.clear()
        items.addAll(results)
        notifyDataSetChanged()
    }

    fun unbind() {
        holders.forEach {
            it.get()?.let { holder -> holder.unbind() }
        }
    }
}