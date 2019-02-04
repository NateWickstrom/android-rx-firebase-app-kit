package media.pixi.rx.algolia.search.ui.search

interface SearchContract {

    interface View {

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