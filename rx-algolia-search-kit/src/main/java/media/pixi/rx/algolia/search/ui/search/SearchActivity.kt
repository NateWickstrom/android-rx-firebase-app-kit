package media.pixi.rx.algolia.search.ui.search

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.algolia.instantsearch.core.helpers.Searcher
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.search__activity_search.*
import media.pixi.common.ActivityUtils
import media.pixi.rx.algolia.search.R
import media.pixi.rx.algolia.search.data.SearchProvider
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchActivity: DaggerAppCompatActivity() {

    lateinit var fragment: SearchFragment
        @Inject set
    lateinit var searchProvider: SearchProvider
        @Inject set
    lateinit var searchNavigator: SearchNavigator
        @Inject set
    lateinit var searchPresenter: SearchPresenter
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search__activity_search)
        setSupportActionBar(toolbar)
        title = ""

        // Initialize a Searcher with your credentials and an index name
        Searcher.destroyAll()

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )

        searchPresenter = SearchPresenter(searchProvider, searchNavigator)
        fragment.presenter = searchPresenter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search__menu, menu)

        //InstantSearch(this, menu, R.id.action_search, searcher) // link the Searcher to the UI
        //searcher.search(intent) // Show results for empty query (on app launch) / voice query (from intent)

        val itemSearch = menu.findItem(R.id.action_search)
        val searchBox = itemSearch.actionView as SearchView
        searchBox.isIconified = false
        searchBox.queryTextChanges()
            .map { it.toString() }
            .filter { it.isNotBlank() }
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribe(
                { searchPresenter.search(it) },
                { Timber.e(it.message, it) }
            )

        return true
    }
}
