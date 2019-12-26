package media.pixi.appkit.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.list.SearcherSingleIndexDataSource
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxConnectorPagedList
import com.algolia.instantsearch.helper.filter.state.FilterState
import com.algolia.instantsearch.helper.searcher.SearcherSingleIndex
import com.algolia.instantsearch.helper.searcher.connectFilterState
import com.algolia.search.client.ClientSearch
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import media.pixi.appkit.BuildConfig


class ViewModel : ViewModel() {

    val appID = ApplicationID(BuildConfig.ALGOLIA_APP_ID)
    val apiKey = APIKey(BuildConfig.ALGOLIA_API_KEY)
    val client = ClientSearch(appID, apiKey)
    val indexName = IndexName("people")
    val index = client.initIndex(indexName)
    val searcher = SearcherSingleIndex(index)

    val dataSourceFactory = SearcherSingleIndexDataSource.Factory(searcher) { hit ->
        User(
            firstname = hit.json.getPrimitive("firstname").content,
            lastname = hit.json.getPrimitive("lastname").content,
            username = hit.json.getPrimitive("username").content,
            imageUrl = hit.json.getPrimitive("imageUrl").content,
            id = hit.json.getPrimitive("id").content,
            _highlightResult = hit.json.getObjectOrNull("_highlightResult")
        )
    }
    val pagedListConfig = PagedList.Config.Builder().setPageSize(50).setEnablePlaceholders(false).build()
    val products: LiveData<PagedList<User>> = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    val searchBox = SearchBoxConnectorPagedList(searcher, listOf(products))
    val adapterProduct = UserAdapter()

    val connection = ConnectionHandler()

    init {
        connection += searchBox
        connection += searcher.connectFilterState(FilterState())
    }

    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
        connection.clear()
    }
}