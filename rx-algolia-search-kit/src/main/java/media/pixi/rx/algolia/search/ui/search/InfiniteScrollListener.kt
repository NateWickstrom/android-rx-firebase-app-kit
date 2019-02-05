package media.pixi.rx.algolia.search.ui.search

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import media.pixi.rx.algolia.search.data.SearchProvider

class InfiniteScrollListener(private val layoutManager: LinearLayoutManager,
                             private val provider: SearchProvider
): RecyclerView.OnScrollListener() {

    /**
     * The minimum number of items remaining below the fold before loading more.
     */
    private val remainingItemsBeforeLoading = 0

    private var lastItemCount = 0 // Item count after last event
    private var currentlyLoading = true // Are we waiting for new results?

    internal fun setCurrentlyLoading(currentlyLoading: Boolean) {
        this.currentlyLoading = currentlyLoading
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!provider.hasMoreHits()) {
            return
        }

        val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
        if (totalItemCount < lastItemCount) {
            // we have less elements than before, the count should be reset
            lastItemCount = totalItemCount
            if (totalItemCount == 0) {
                // the list is empty -> do nothing until we get more results.
                setCurrentlyLoading(true)
                return
            }
        }

        if (currentlyLoading) {
            if (totalItemCount > lastItemCount) {
                // the data changed, loading is finished
                setCurrentlyLoading(false)
                lastItemCount = totalItemCount
            }
        } else {
            val lastVisiblePosition = getLastVisibleItemPosition()

            if (lastVisiblePosition + remainingItemsBeforeLoading > totalItemCount) {
                // we are under the loading threshold, let's load more data
                provider.loadMore()
                setCurrentlyLoading(true)
            }
        }
    }

    /**
     * Calculate the position of last visible item, notwithstanding the LayoutManager's class.
     *
     * @return the last visible item's position in the list.
     */
    private fun getLastVisibleItemPosition(): Int {
        var lastVisiblePosition = 0
        lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
        return lastVisiblePosition
    }
}