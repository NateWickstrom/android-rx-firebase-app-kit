package media.pixi.appkit.ui.search

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.algolia.instantsearch.core.helpers.Searcher
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__activity_search.*
import media.pixi.appkit.R
import media.pixi.appkit.data.search.SearchProvider
import media.pixi.appkit.utils.ActivityUtils
import timber.log.Timber
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
        setContentView(R.layout.appkit__activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        menuInflater.inflate(R.menu.appkit__search_menu, menu)

        //InstantSearch(this, menu, R.id.action_search, searcher) // link the Searcher to the UI
        //searcher.search(intent) // Show results for empty query (on app launch) / voice query (from intent)

        val itemSearch = menu.findItem(R.id.action_search)
        val searchBox = itemSearch.actionView as SearchView
        searchBox.isIconified = false
        searchBox.queryTextChanges()
            .map { it.toString() }
            //.debounce(DEBOUNCE_MILLISECONDS, TimeUnit.MILLISECONDS)
            .subscribe(
                { searchPresenter.search(it) },
                { Timber.e(it.message, it) }
            )

        return true
    }

    companion object {
        private const val DEBOUNCE_MILLISECONDS = 300L
    }
}
