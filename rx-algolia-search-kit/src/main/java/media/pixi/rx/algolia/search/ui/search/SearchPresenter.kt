package media.pixi.rx.algolia.search.ui.search

import io.reactivex.disposables.Disposable
import media.pixi.rx.algolia.search.data.SearchProvider
import timber.log.Timber
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val searchProvider: SearchProvider,
                                          private val navigator: SearchNavigator):
    SearchContract.Presenter {

    var disposable: Disposable? = null

    override fun takeView(view: SearchContract.View) {
        disposable = searchProvider.people()
            .subscribe(
                {
                    Timber.d(it.toString()) },
                {
                    Timber.e(it.message, it) }
            )
    }

    override fun dropView() {
        disposable?.dispose()
    }

    override fun search(query: String) {
        searchProvider.search(query)
    }
}