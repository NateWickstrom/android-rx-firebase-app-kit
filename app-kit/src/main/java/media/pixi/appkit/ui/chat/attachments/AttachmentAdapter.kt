package media.pixi.appkit.ui.chat.attachments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import media.pixi.appkit.R
import media.pixi.appkit.domain.chats.models.MessageAttachment
import media.pixi.appkit.domain.chats.models.MessageAttachmentType
import java.lang.IllegalArgumentException

class AttachmentAdapter(
    private val onClickListener: (position: Int, item: MessageAttachment) -> Unit,
    private val onDeleteClickListener: (position: Int, item: MessageAttachment) -> Unit
) : RecyclerView.Adapter<AttachmentViewHolder>() {

    private val list = mutableListOf<MessageAttachment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachmentViewHolder {
        val layout = when(viewType) {
            IMAGE -> R.layout.view_attachment_image_item
            VIDEO -> R.layout.view_attachment_video_item
            else -> throw IllegalArgumentException("unknown view type")
        }
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layout, parent, false)
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

    override fun getItemViewType(position: Int): Int {
        return when (list[position].type) {
            MessageAttachmentType.IMAGE -> IMAGE
            MessageAttachmentType.VIDEO -> VIDEO
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

    fun get(): List<MessageAttachment> {
        return list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        private const val VIDEO = 0
        private const val IMAGE = 1
    }
}