package media.pixi.appkit.ui.chats

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.R
import media.pixi.appkit.data.chats.ChatEntity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.domain.chats.models.ChatListItem
import media.pixi.appkit.domain.chats.ChatListItemsGetter
import media.pixi.appkit.ui.ClusterLayout
import media.pixi.appkit.utils.DateUtils
import org.joda.time.DateTime
import timber.log.Timber


class ChatViewHolder(itemView: View, private val chats: ChatListItemsGetter): RecyclerView.ViewHolder(itemView) {

    private val defaultTypeface = itemView.findViewById<TextView>(R.id.title).typeface
    private var disposables = CompositeDisposable()

    private var image: ClusterLayout? = null
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

    private fun onResult(chat: ChatListItem) {
        image?.setClusteredViews(toClusteredView(chat.users))
        subtitle?.text = chat.subtitle
        time?.text = getTime(chat.time)

        title?.text = chat.title

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

    private fun isTruncated(textView: TextView): Boolean {
        val layout = textView.layout
        if (layout != null) {
            val lines = layout.lineCount
            if (lines > 0) {
                val ellipsisCount = layout.getEllipsisCount(lines - 1)
                if (ellipsisCount > 0) {
                    return true
                }
            }
        }
        return false
    }

    private fun toClusteredView(users: List<UserProfile>): MutableList<ClusterLayout.ClusteredView> {
        val blue = itemView.resources.getColor(R.color.blue)
        val green = itemView.resources.getColor(R.color.green)
        val pink = itemView.resources.getColor(R.color.pink)
        val orange = itemView.resources.getColor(R.color.orange)
        val colors = mutableListOf(blue, green, pink, orange)
        colors.shuffle()

        return users.mapIndexed { index, userProfile ->
            MyClusteredView(
                image = userProfile.imageUrl,
                name = userProfile.firstName[0].toString(),
                backgroundColor = colors[index % 4]
            )
        }.toMutableList()
    }

    internal data class MyClusteredView(
        private val image: String,
        private val name: String,
        private val backgroundColor: Int

    ): ClusterLayout.ClusteredView {
        override fun getImageUrl(): String {
            return image
        }

        override fun getName(): String {
            return name
        }

        override fun getBackgroundColor(): Int {
            return backgroundColor
        }
    }

    companion object {
        val LAYOUT_ID = R.layout.appkit__item_chat
    }
}