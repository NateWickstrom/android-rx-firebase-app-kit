package media.pixi.rx.algolia.search.data

import io.reactivex.Observable

interface SearchProvider {

    fun search(query: String)

    fun loadMore()

    fun people(): Observable<PeopleSearchResult>

}