package media.pixi.appkit.data.search

import io.reactivex.Observable

interface SearchProvider {

    fun search(query: String)

    fun hasMoreHits(): Boolean

    fun loadMore()

    fun people(): Observable<PeopleSearchResult>

    fun hasPendingRequests(): Boolean
}