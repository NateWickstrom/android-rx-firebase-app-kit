package media.pixi.rx.algolia.search.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search__item_person.view.*
import media.pixi.rx.algolia.search.R
import media.pixi.rx.algolia.search.data.PeopleSearchResult
import media.pixi.rx.algolia.search.data.PersonSearchResult
import java.util.ArrayList

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    val onClickListener: ((Int) -> Unit)? = null
    val onLongClickListener: ((Int) -> Boolean)? = null

    private val hits = ArrayList<PersonSearchResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.search__item_person, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItemAt(position))
        holder.itemView.setOnClickListener { onClickListener?.invoke(position) }
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
            name.text = item.name
            //itemImage.loadUrl(item.url)
        }
    }
}