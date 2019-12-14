package media.pixi.appkit.ui.chats

import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.R
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.domain.chats.ChatItem
import media.pixi.appkit.domain.chats.GetChats
import media.pixi.appkit.utils.DateUtils
import media.pixi.appkit.utils.ImageUtils
import org.joda.time.DateTime
import timber.log.Timber


class ChatViewHolder(itemView: View, private val chats: GetChats): RecyclerView.ViewHolder(itemView) {

    private val defaultTypeface = itemView.findViewById<TextView>(R.id.title).typeface
    private var disposables = CompositeDisposable()

    private var image: ImageView? = null
    private var title: TextView? = null
    private var subtitle: TextView? = null
    private var time: TextView? = null

    fun bind(item: ChatEntity) = with(itemView) {
        unbind()

        image = itemView.findViewById(R.id.item_image)
        title = itemView.findViewById(R.id.title)
        subtitle = itemView.findViewById(R.id.subtitle)
        time = itemView.findViewById(R.id.time)

        disposables.add(
            chats.getChatItem(item)
                .subscribe(
                    { onResult(it) },
                    { onError(it) }
                )
        )
    }

    fun unbind() {
        disposables.clear()
    }

    private fun onResult(chat: ChatItem) {
        for (users in chat.profileImageUrls) {
            ImageUtils.setUserImage(image!!, chat.profileImageUrls[0])
        }
        title?.text = chat.title
        subtitle?.text = chat.subtitle
        time?.text = getTime(chat.time)

        if (chat.hasSeen) {
            setPlane(title)
            setPlane(subtitle)
            setPlane(time)
        } else {
            setBold(title)
            setBold(subtitle)
            setBold(time)
        }
    }

    private fun onError(error: Throwable) {
        Timber.e(error)
        title?.text = "oops"
        subtitle?.text = ""
    }

    private fun getTime(time: DateTime): String {
        return DateUtils.toPrettyString(time.toDate())
    }

    private fun setBold(textView: TextView?) {
        textView?.let {
            it.setTypeface(it.typeface, Typeface.BOLD)
        }
    }

    private fun setPlane(textView: TextView?) {
        textView?.let {
            it.setTypeface(defaultTypeface, Typeface.NORMAL)
        }
    }

    companion object {
        val LAYOUT_ID = R.layout.appkit__item_chat
    }
}