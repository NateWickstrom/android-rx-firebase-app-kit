package media.pixi.appkit.ui.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.appkit__item_person.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.utils.ImageUtils
import java.util.*

class FriendsAdapter: RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    var onClickListener: ((UserProfile) -> Unit)? = null
    var onLongClickListener: ((Int) -> Boolean)? = null

    private val hits = ArrayList<UserProfile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.appkit__item_person, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItemAt(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onClickListener?.invoke(item) }
        holder.itemView.setOnLongClickListener { onLongClickListener?.invoke(position) ?: false}
    }

    fun add(result: UserProfile) {
        hits.add(result)
    }

    private fun getItemAt(position: Int): UserProfile {
        return hits[position]
    }

    fun addAll(results: Iterable<UserProfile>) {
        hits.addAll(results)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: UserProfile) = with(itemView) {
            ImageUtils.setUserImage(user_image, item.imageUrl)
            username.text = item.username
            name.text = itemView.context.getString(R.string.appkit__username, item.firstName, item.lastName)
        }
    }
}