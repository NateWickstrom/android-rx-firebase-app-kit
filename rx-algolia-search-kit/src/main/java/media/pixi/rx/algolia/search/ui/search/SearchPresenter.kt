package media.pixi.rx.algolia.search.ui.search

import media.pixi.rx.algolia.search.data.SearchProvider
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val searchProvider: SearchProvider,
                                          private val navigator: SearchNavigator): SearchContract.Presenter {

    override fun takeView(view: SearchContract.View) {

    }

    override fun dropView() {

    }
}