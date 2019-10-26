package media.pixi.appkit.ui.chatcreator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.appkit__item_person.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.profile.UserProfile

class ContactsAdapter: RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    var onClickListener: ((UserProfile) -> Unit)? = null
    var onLongClickListener: ((Int) -> Boolean)? = null

    private val items = mutableListOf<UserProfile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.appkit__item_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onClickListener?.invoke(item) }
        holder.itemView.setOnLongClickListener { onLongClickListener?.invoke(position) ?: false}
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.hashCode().toLong()
    }

    fun set(results: List<UserProfile>) {
        items.clear()
        items.addAll(results)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: UserProfile) = with(itemView) {
            //ImageUtils.setUserImage(item_image, item.item_image)
            username.text = item.username
            name.text = item.firstName
        }
    }
}