package media.pixi.rx.algolia.search.ui.search

import media.pixi.rx.algolia.search.data.PeopleSearchResult

interface SearchContract {

    interface View {
        fun addHits(results: PeopleSearchResult)
        fun clear(shouldNotify: Boolean)
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