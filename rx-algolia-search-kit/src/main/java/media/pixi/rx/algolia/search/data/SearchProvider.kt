package media.pixi.rx.algolia.search.data

import io.reactivex.Observable

interface SearchProvider {

    fun search(query: String)

    fun hasMoreHits(): Boolean

    fun loadMore()

    fun people(): Observable<PeopleSearchResult>

    fun hasPendingRequests(): Boolean
}