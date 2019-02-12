package media.pixi.appkit.ui.search

import android.app.Activity
import io.reactivex.disposables.Disposable
import media.pixi.appkit.data.search.PeopleSearchResult
import media.pixi.appkit.data.search.PersonSearchResult
import media.pixi.appkit.data.search.SearchProvider
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
        view.clear(true)
        view.loading = false
    }

    override fun dropView() {
        this.view = null
        disposable?.dispose()
        disposable = null
    }

    override fun search(query: String) {
        if (query.isBlank()) {
            view?.clear(true)
        } else {
            view?.loading = true
            if (disposable == null) {
                disposable = searchProvider.people()
                    .subscribe(
                        { onResult(it) },
                        { onError(it) }
                    )
            }
            searchProvider.search(query)
        }
    }

    override fun onListItemClicked(activity: Activity, personSearchResult: PersonSearchResult) {
        navigator.showProfile(activity, personSearchResult)
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