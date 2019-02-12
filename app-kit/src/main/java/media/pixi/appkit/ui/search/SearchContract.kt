package media.pixi.appkit.ui.search

import android.app.Activity
import media.pixi.appkit.data.search.PeopleSearchResult
import media.pixi.appkit.data.search.PersonSearchResult


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

        fun onListItemClicked(activity: Activity, personSearchResult: PersonSearchResult)
    }

    interface Navigator {
        fun showProfile(activity: Activity, user: PersonSearchResult)
    }
}