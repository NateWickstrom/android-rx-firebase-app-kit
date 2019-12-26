package media.pixi.appkit.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import media.pixi.appkit.R


class UserAdapter : PagedListAdapter<User, UserViewHolder>(
    UserAdapter
) {

    var onClickListener: ((User) -> Unit)? = null
    var onLongClickListener: ((Int) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appkit__item_person, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener { onClickListener?.invoke(item) }
            holder.itemView.setOnLongClickListener {
                onLongClickListener?.invoke(position) ?: false
            }
        }
    }

    companion object : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem::class == newItem::class
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}