package media.pixi.appkit.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.appkit__item_notification.view.*
import media.pixi.appkit.R
import media.pixi.appkit.domain.notifications.Notification
import media.pixi.appkit.utils.ImageUtils
import java.util.*

class NotificationsAdapter: RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    var onClickListener: ((Int) -> Unit)? = null
    var onLongClickListener: ((Int) -> Unit)? = null
    var onActionClickListener: ((Int) -> Unit)? = null

    private val hits = ArrayList<Notification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.appkit__item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItemAt(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onClickListener?.invoke(position) }
        holder.itemView.setOnLongClickListener { onLongClickListener?.invoke(position); true }
        holder.itemView.action.setOnClickListener { onActionClickListener?.invoke(position) }

    }

    fun add(result: Notification) {
        hits.add(result)
    }

    fun add(results: List<Notification>) {
        hits.addAll(results)
        notifyDataSetChanged()
    }

    private fun getItemAt(position: Int): Notification {
        return hits[position]
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: Notification) = with(itemView) {
            ImageUtils.setUserImage(itemView.image, item.imageUrl)
            itemView.title.text = item.title
            itemView.subtitle.text = item.subtitle
            itemView.action.text = "accept"
        }
    }
}