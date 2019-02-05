package media.pixi.rx.algolia.search.ui.search

import media.pixi.rx.algolia.search.data.PeopleSearchResult

interface SearchContract {

    interface View {
        var loading: Boolean

        fun addHits(results: PeopleSearchResult)
        fun clear(shouldNotify: Boolean)
        fun showNoResults(query: String)
        fun showEmptyState()
        fun showResults()
    }

    interface Presenter {
        /**
         * Binds presenter with a view when resumed. The Presenter will perform initialization here.
         *
         * @param view the view associated with this presenter
         */
        fun takeView(view: View)

        /**
         * Drops the reference to the view when destroyed
         */
        fun dropView()

        fun search(query: String)
    }

    interface Navigator {

    }
}