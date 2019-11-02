package media.pixi.appkit.ui.chat.viewholders

import android.view.View
import com.facebook.drawee.view.SimpleDraweeView
import media.pixi.appkit.R
import media.pixi.appkit.ui.chat.MessageListItem

class ImageMessageViewHolder(itemView: View): MessageViewHolder (itemView) {

    private var messageImageView: SimpleDraweeView = itemView.findViewById(R.id.image_message_image)

    override fun bind(messageItem: MessageListItem) {
        super.bind(messageItem)

        val url = messageItem.message.message
        messageImageView.setImageURI(url)
    }

    override fun setOnClickListener(onClickListener: (View) -> Unit) {
        messageImageView.setOnClickListener(onClickListener)
    }

}