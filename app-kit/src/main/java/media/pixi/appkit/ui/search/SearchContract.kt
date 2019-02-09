package media.pixi.appkit.ui.search

import media.pixi.appkit.data.search.PeopleSearchResult


interface SearchContract {

    interface View {
        var loading: Boolean

        fun addHits(results: PeopleSearchResult)
        fun clear(shouldNotify: Boolean)
        fun showNoResults(show: Boolean)
        fun showEmptyState(show: Boolean)
        fun showResults(show: Boolean)
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