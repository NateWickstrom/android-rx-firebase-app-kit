package media.pixi.appkit.ui.chat.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import media.pixi.appkit.R
import media.pixi.appkit.domain.chats.Message
import media.pixi.appkit.domain.chats.MessageSendStatus
import media.pixi.appkit.ui.chat.MessageListItem
import java.text.SimpleDateFormat
import java.util.*

abstract class MessageViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    protected var avatarImageView: SimpleDraweeView = itemView.findViewById(R.id.image_avatar)
    protected var timeTextView: TextView = itemView.findViewById(R.id.text_time)
    protected var messageImageView: SimpleDraweeView = itemView.findViewById(R.id.image_message_image)
    protected var messageBubble: ConstraintLayout = itemView.findViewById(R.id.image_message_bubble)
    protected var messageTextView: TextView = itemView.findViewById(R.id.text_content)
//    protected var messageIconView: SimpleDraweeView = itemView.findViewById(R.id.image_icon)
    protected var extraLayout: LinearLayout = itemView.findViewById(R.id.layout_extra)
    //protected var readReceiptImageView: ImageView = itemView.findViewById(R.id.image_read_receipt)
    protected var progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

    private lateinit var message: Message

    open fun bind(messageItem: MessageListItem) {
        this.message = messageItem.message

        setBubbleHidden(true)
        setTextHidden(true)
        setIconHidden(true)
        setImageHidden(true)

        val alpha =
            if (message.messageSendStatus === MessageSendStatus.Sent
                || message.messageSendStatus === MessageSendStatus.Delivered) 1.0f else 0.7f
        setAlpha(alpha)

        val time = getTimeFormat(message).format(message.date.toDate()).toString()
        timeTextView.text = time

        avatarImageView.setImageURI(messageItem.sendIconUrl)

//        if (messageItem.isMe) {
//            messageBubble.background.setColorFilter(
//                ChatSDK.config().messageColorMe,
//                PorterDuff.Mode.MULTIPLY
//            )
//        } else {
//            messageBubble.background.setColorFilter(
//                ChatSDK.config().messageColorReply,
//                PorterDuff.Mode.MULTIPLY
//            )
//        }

        updateReadStatus()

    }

    protected fun updateReadStatus() {
//        var resource = R.drawable.ic_done_24px
//        var status = message.messageReadStatus
//
//        // Hide the read receipt for public threads
//        if (message.getThread().typeIs(ThreadType.Public) || ChatSDK.readReceipts() == null) {
//            status = MessageReadStatus.hide()
//        }
//
//        if (status.`is`(MessageReadStatus.delivered())) {
//            resource = R.drawable.ic_done_24px
//        }
//        if (status.`is`(MessageReadStatus.read())) {
//            resource = R.drawable.ic_done_all_24px
//        }
//        if (readReceiptImageView != null) {
//            readReceiptImageView.setImageResource(resource)
//            readReceiptImageView.visibility =
//                if (status.`is`(MessageReadStatus.hide())) View.INVISIBLE else View.VISIBLE
//        }
    }

    fun setAlpha(alpha: Float) {
        messageImageView.alpha = alpha
        messageTextView.alpha = alpha
        extraLayout.alpha = alpha
    }

    fun maxWidth(): Int {
        return itemView.getResources().getDimensionPixelSize(R.dimen.message_image_max_width)
    }

    fun maxHeight(): Int {
        return itemView.getResources().getDimensionPixelSize(R.dimen.message_image_max_height)
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        progressBar.isIndeterminate = true
        progressBar.bringToFront()
    }

    fun showProgressBar(progress: Float) {
        if (progress == 0f) {
            showProgressBar()
        } else {
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = false
            progressBar.max = 100
            progressBar.progress = Math.ceil((progress * progressBar.max).toDouble()).toInt()
            progressBar.bringToFront()
        }
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun setIconSize(width: Int, height: Int) {
//        messageIconView.layoutParams.width = width
//        messageIconView.layoutParams.height = height
//        messageIconView.requestLayout()
    }

    fun setIconMargins(start: Int, top: Int, end: Int, bottom: Int) {
//        (messageIconView.layoutParams as ConstraintLayout.LayoutParams).setMargins(
//            start,
//            top,
//            end,
//            bottom
//        )
//        messageIconView.requestLayout()
    }

    fun setImageSize(width: Int, height: Int) {
        messageImageView.layoutParams.width = width
        messageImageView.layoutParams.height = height
        messageImageView.requestLayout()
    }

    fun setBubbleHidden(hidden: Boolean) {
        messageBubble.visibility = if (hidden) View.INVISIBLE else View.VISIBLE
        messageBubble.layoutParams.width = if (hidden) 0 else ViewGroup.LayoutParams.WRAP_CONTENT
        messageBubble.layoutParams.height = if (hidden) 0 else ViewGroup.LayoutParams.WRAP_CONTENT
        messageBubble.requestLayout()
    }

    fun setIconHidden(hidden: Boolean) {
//        messageIconView.visibility = if (hidden) View.INVISIBLE else View.VISIBLE
//        if (hidden) {
//            setIconSize(0, 0)
//        } else {
//            setIconSize(
//                itemView.getResources().getDimensionPixelSize(R.dimen.message_icon_max_width),
//                itemView.getResources().getDimensionPixelSize(R.dimen.message_icon_max_height)
//            )
//        }
//        messageBubble.requestLayout()
    }

    fun setImageHidden(hidden: Boolean) {
        messageImageView.visibility = if (hidden) View.INVISIBLE else View.VISIBLE
        if (hidden) {
            setImageSize(0, 0)
        } else {
            //                setImageSize(maxWidth(), maxHeight());
            setImageSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

    }

    fun setTextHidden(hidden: Boolean) {
        messageTextView.visibility = if (hidden) View.INVISIBLE else View.VISIBLE
        val textLayoutParams = messageTextView.layoutParams as ConstraintLayout.LayoutParams
        if (hidden) {
            textLayoutParams.width = 0
            textLayoutParams.height = 0
        } else {
            textLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            textLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        messageTextView.layoutParams = textLayoutParams
        messageTextView.requestLayout()
        messageBubble.requestLayout()
    }

    protected fun getTimeFormat(message: Message): SimpleDateFormat {

        val curTime = Date()
        val interval = (curTime.time - message.date.toDate().time) / 1000L

        val simpleDateFormat = SimpleDateFormat(MessageTimeFormat, Locale.getDefault())

        // More then a day ago
        if (interval < 3600 * 24) {
            return simpleDateFormat
        } else if (interval < 3600 * 24 * 7) {
            simpleDateFormat.applyPattern("$MessageTimeFormat $DayToWeekFormat")
        } else if (interval < 3600 * 24 * 365) {
            simpleDateFormat.applyPattern("$MessageTimeFormat $WeekToYearFormat")
        } else {
            simpleDateFormat.applyPattern("$MessageTimeFormat $MoreThanYearFormat")
        }
        return simpleDateFormat
    }
    companion object {
        const val MoreThanYearFormat = "MM/yy"
        const val WeekToYearFormat = "dd/MM"
        const val DayToWeekFormat = "EEE"
        const val MessageTimeFormat = "HH:mm"
    }
}