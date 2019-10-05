package media.pixi.appkit.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.appkit__item_person.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.search.PeopleSearchResult
import media.pixi.appkit.data.search.PersonSearchResult
import media.pixi.appkit.utils.ImageUtils
import java.util.*


class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var onClickListener: ((PersonSearchResult) -> Unit)? = null
    var onLongClickListener: ((Int) -> Boolean)? = null

    private val hits = ArrayList<PersonSearchResult>()

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

    internal fun clear() {
        clear(true)
    }

    fun add(result: PersonSearchResult) {
        hits.add(result)
    }

    fun getItemAt(position: Int): PersonSearchResult {
        return hits[position]
    }

    /**
     * Adds or replaces hits to/in this widget.
     *
     * @param results     A [JSONObject] containing hits.
     */
    fun addHits(results: PeopleSearchResult) {
        val hits = results.hits

        if (results.page == 0) {
            clear(hits.isEmpty())
            hits.forEach { add(it) }
            notifyDataSetChanged()
            //smoothScrollToPosition(0)
            //infiniteScrollListener.setCurrentlyLoading(false)
        } else {
            hits.forEach { add(it) }
            notifyItemRangeInserted(results.page * results.hitsPerPage, hits.size)
        }
    }

    internal fun clear(shouldNotify: Boolean) {
        if (shouldNotify) {
            hits.clear()
            notifyDataSetChanged()
        } else {
            hits.clear()
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: PersonSearchResult) = with(itemView) {
            ImageUtils.setUserImage(user_image, item.imageUrl ?: "")
            username.text = item.username
            name.text = itemView.context.getString(R.string.appkit__username, item.firstname, item.lastname)
        }
    }
}