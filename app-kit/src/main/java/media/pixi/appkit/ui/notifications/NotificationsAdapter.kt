package media.pixi.appkit.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.appkit__item_notification_friend_request.view.*
import media.pixi.appkit.R
import media.pixi.appkit.domain.notifications.FriendRequestNotification
import media.pixi.appkit.domain.notifications.MyNotification
import media.pixi.appkit.utils.ImageUtils
import java.util.*

class NotificationsAdapter(): RecyclerView.Adapter<NotificationsAdapter.NotificationListItemViewHolder>() {

    var onClickListener: ((MyNotification, Int) -> Unit)? = null
    var onLongClickListener: ((MyNotification, Int) -> Unit)? = null
    var onActionClickListener: ((MyNotification, Int) -> Unit)? = null

    private val hits = ArrayList<MyNotification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListItemViewHolder {
        val layout = when(viewType) {
            TYPE_FRIEND_REQUEST -> R.layout.appkit__item_notification_friend_request
            else -> R.layout.appkit__item_notification_new_friend
        }
        val view  = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return when(viewType) {
            TYPE_FRIEND_REQUEST -> FriendRequest(view)
            else -> NewFriend(view)
        }
    }

    override fun getItemCount(): Int {
        return hits.size
    }

    override fun onBindViewHolder(holder: NotificationListItemViewHolder, position: Int) {
        val item = getItemAt(position)
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return when(hits[position]) {
            is FriendRequestNotification -> TYPE_FRIEND_REQUEST
            else -> TYPE_NEW_FRIEND
        }
    }

    fun add(result: MyNotification) {
        hits.add(result)
        notifyDataSetChanged()
    }

    fun set(position: Int, result: MyNotification) {
        hits[position] = result
        notifyDataSetChanged()
    }

    fun add(results: List<MyNotification>) {
        hits.addAll(results)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        hits.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun getItemAt(position: Int): MyNotification {
        return hits[position]
    }

    abstract class NotificationListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: MyNotification)
    }

    inner class FriendRequest(itemView: View): NotificationListItemViewHolder(itemView) {

        override fun bind(item: MyNotification) = with(itemView) {
            ImageUtils.setUserImage(itemView.image, item.imageUrl)
            itemView.title.text = item.title
            itemView.subtitle.text = item.subtitle
            itemView.action.text = "accept"
            itemView.action.isEnabled = true

            itemView.setOnClickListener { onClickListener?.invoke(item, adapterPosition) }
            itemView.setOnLongClickListener { onLongClickListener?.invoke(item, adapterPosition); true }
            itemView.action.setOnClickListener {
                itemView.action.isEnabled = false
                onActionClickListener?.invoke(item, adapterPosition)
            }
        }
    }

    inner class NewFriend(itemView: View): NotificationListItemViewHolder(itemView) {

        override fun bind(item: MyNotification) = with(itemView) {
            ImageUtils.setUserImage(itemView.image, item.imageUrl)
            itemView.title.text = item.title
            itemView.subtitle.text = item.subtitle

            itemView.setOnClickListener { onClickListener?.invoke(item, adapterPosition) }
            itemView.setOnLongClickListener { onLongClickListener?.invoke(item, adapterPosition); true }
        }
    }

    companion object {
        private const val TYPE_FRIEND_REQUEST = 0
        private const val TYPE_NEW_FRIEND = 1
    }

}