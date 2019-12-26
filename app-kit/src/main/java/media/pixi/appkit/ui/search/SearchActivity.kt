package media.pixi.appkit.ui.search

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxViewAppCompat
import com.algolia.instantsearch.helper.android.searchbox.connectView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__activity_search.*
import media.pixi.appkit.R
import media.pixi.appkit.ui.userprofile.UserProfileActivity
import media.pixi.appkit.utils.ActivityUtils

class SearchActivity: DaggerAppCompatActivity() {

    private val connection = ConnectionHandler()

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""

        val fragment = SearchFragment()
        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )

        viewModel = ViewModelProviders.of(this)[ViewModel::class.java]
        fragment.viewModel = viewModel

        viewModel.adapterProduct.onClickListener  = {
            user ->  UserProfileActivity.launch(this, user.id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appkit__search_menu, menu)

        val itemSearch = menu.findItem(R.id.action_search)
        val searchBox = itemSearch.actionView as SearchView
        searchBox.isIconified = false
        val searchBoxView = SearchBoxViewAppCompat(searchBox)

        connection += viewModel.searchBox.connectView(searchBoxView)

        return true
    }
}
