package media.pixi.appkit.data.search

import com.algolia.instantsearch.core.helpers.Searcher
import com.algolia.instantsearch.core.model.AlgoliaErrorListener
import com.algolia.instantsearch.core.model.AlgoliaResultsListener
import com.algolia.instantsearch.core.model.SearchResults
import com.algolia.search.saas.AlgoliaException
import com.algolia.search.saas.Query

import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import media.pixi.appkit.BuildConfig
import timber.log.Timber
import javax.inject.Inject

class AlgoliaSearchDataSource @Inject constructor(): SearchProvider {

    private val myListener = MyListener()

    private var searcher: Searcher? = null

    override fun search(query: String) {
        if (searcher == null) {
            initSearch()
        }
        searcher?.search(query)
    }

    override fun hasMoreHits(): Boolean {
        return searcher?.hasMoreHits() ?: false
    }

    override fun loadMore() {
        searcher?.loadMore()
    }

    override fun people(): Observable<PeopleSearchResult> {
        return myListener.subject.toSerialized()
    }

    override fun hasPendingRequests(): Boolean {
        return searcher?.hasPendingRequests() ?: false
    }

    private fun initSearch() {
        val appId = BuildConfig.ALGOLIA_APP_ID
        val apiKey = BuildConfig.ALGOLIA_API_KEY

        searcher?.destroy()
        searcher = Searcher.create(appId, apiKey, CONTACTS)
        searcher?.registerErrorListener(myListener)
        searcher?.registerResultListener(myListener)
    }

    class MyListener: AlgoliaErrorListener, AlgoliaResultsListener {

        val gson = Gson()
        val subject: Subject<PeopleSearchResult> = BehaviorSubject.create()

        override fun onResults(results: SearchResults, isLoadingMore: Boolean) {
            Timber.d(results.toString())
            val searchResult = gson.fromJson(results.content.toString(), PeopleSearchResult::class.java)
            subject.onNext(searchResult)
        }

        override fun onError(query: Query, error: AlgoliaException) {
            Timber.d(error.message, error)
        }
    }

    companion object {
        private const val CONTACTS = "people"
    }
}
