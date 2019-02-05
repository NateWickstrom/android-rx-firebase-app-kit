package media.pixi.rx.algolia.search.ui.search

import io.reactivex.disposables.Disposable
import media.pixi.rx.algolia.search.data.PeopleSearchResult
import media.pixi.rx.algolia.search.data.SearchProvider
import timber.log.Timber
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val searchProvider: SearchProvider,
                                          private val navigator: SearchNavigator):
    SearchContract.Presenter {

    private var disposable: Disposable? = null
    private var view: SearchContract.View? = null

    private var lastResult = ""

    override fun takeView(view: SearchContract.View) {
        this.view = view
        disposable = searchProvider.people()
            .subscribe(
                { onResult(it) },
                { onError(it) }
            )
    }

    override fun dropView() {
        this.view = null
        disposable?.dispose()
    }

    override fun search(query: String) {
        if (query.isBlank()) {
            view?.clear(true)
        } else {
            view?.loading = true
            searchProvider.search(query)
        }
    }

    private fun onResult(result: PeopleSearchResult) {
        view?.loading = searchProvider.hasPendingRequests()
        view?.addHits(result)

        view?.showNoResults(result.nbHits == 0)

        lastResult = result.query
    }

    private fun onError(error: Throwable) {
        view?.loading = searchProvider.hasPendingRequests()
        Timber.e(error.message, error)
    }

}