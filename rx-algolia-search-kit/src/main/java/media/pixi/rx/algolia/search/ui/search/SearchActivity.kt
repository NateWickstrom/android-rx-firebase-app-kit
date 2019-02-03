package media.pixi.rx.algolia.search.ui.search

import android.os.Bundle
import com.algolia.instantsearch.core.helpers.Searcher
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.search__activity_search.*
import media.pixi.common.ActivityUtils
import media.pixi.rx.algolia.search.R
import media.pixi.rx.algolia.search.data.SearchProvider
import javax.inject.Inject

class SearchActivity: DaggerAppCompatActivity() {

    lateinit var fragment: SearchFragment
        @Inject set
    lateinit var searchProvider: SearchProvider
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search__activity_search)
        setSupportActionBar(toolbar)

        // Initialize a Searcher with your credentials and an index name
        Searcher.destroyAll()

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }
}