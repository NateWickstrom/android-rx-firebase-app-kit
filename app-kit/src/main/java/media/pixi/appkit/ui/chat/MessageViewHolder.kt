package media.pixi.appkit.ui.chat

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import media.pixi.appkit.R
import media.pixi.appkit.domain.chats.Message
import java.text.SimpleDateFormat
import java.util.*

class MessageViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    private var avatarImageView: SimpleDraweeView = itemView.findViewById(R.id.image_avatar)
    private var timeTextView: TextView = itemView.findViewById(R.id.text_time)
    private var progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
    private var messageTextView: TextView = itemView.findViewById(R.id.text_content)

    private lateinit var message: Message

    open fun bind(messageItem: MessageListItem, showTimeStamp: Boolean) {
        this.message = messageItem.message
        messageTextView.text = messageItem.message.message

        if (showTimeStamp) {
            val time = getTimeFormat(message).format(message.date.toDate()).toString()
            timeTextView.text = time
            timeTextView.visibility = View.VISIBLE
        } else {
            timeTextView.visibility = View.GONE
        }

        avatarImageView.setImageURI(messageItem.sendIconUrl)

        updateReadStatus()
    }

    fun setOnClickListener(onClickListener: (View) -> Unit) {
        messageTextView.setOnClickListener(onClickListener)
    }

    private fun updateReadStatus() {
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

    private fun getTimeFormat(message: Message): SimpleDateFormat {

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