package media.pixi.rx.algolia.search.data

import com.algolia.instantsearch.core.helpers.Searcher

interface SearchProvider {

    fun createUserSearch(): Searcher

}