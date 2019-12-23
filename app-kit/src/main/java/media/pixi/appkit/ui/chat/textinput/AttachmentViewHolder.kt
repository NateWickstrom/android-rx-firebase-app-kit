package media.pixi.appkit.ui.chat.textinput

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import media.pixi.appkit.R
import media.pixi.appkit.domain.chats.models.MessageAttachment

class AttachmentViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    var deleteView: View? = null
    var imageView: ImageView? = null

    fun bind(item: MessageAttachment) = with(itemView) {

        deleteView = findViewById(R.id.attachment_delete)
        imageView = findViewById(R.id.attachment_image)

        Glide.with(this).load(item.imageUrl).into(imageView!!)
    }
}