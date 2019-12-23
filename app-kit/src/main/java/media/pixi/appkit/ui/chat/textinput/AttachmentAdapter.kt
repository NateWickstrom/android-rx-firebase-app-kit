package media.pixi.appkit.ui.chat.textinput

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import media.pixi.appkit.R
import media.pixi.appkit.domain.chats.models.MessageAttachment

class AttachmentAdapter(
    private val onClickListener: (position: Int, item: MessageAttachment) -> Unit,
    private val onDeleteClickListener: (position: Int, item: MessageAttachment) -> Unit
) : RecyclerView.Adapter<AttachmentViewHolder>() {

    private val list = mutableListOf<MessageAttachment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_chat_text_input_attachment, parent, false)
        return AttachmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttachmentViewHolder, position: Int) {
        val attachment = list[position]
        holder.bind(attachment)

        holder.itemView.setOnClickListener {
            onClickListener.invoke(position, attachment)
        }
        holder.deleteView?.setOnClickListener {
            onDeleteClickListener.invoke(position, attachment)
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun add(messageAttachment: MessageAttachment) {
        list.add(messageAttachment)
        notifyDataSetChanged()
    }

    fun add(messageAttachments: List<MessageAttachment>) {
        list.addAll(messageAttachments)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}