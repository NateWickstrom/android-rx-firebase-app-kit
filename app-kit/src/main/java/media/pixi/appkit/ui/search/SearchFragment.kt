package media.pixi.appkit.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_search.*
import kotlinx.android.synthetic.main.appkit__fragment_search.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.search.PeopleSearchResult
import javax.inject.Inject

class SearchFragment @Inject constructor(): DaggerFragment(), SearchContract.View {

    override var loading: Boolean
        get() = progress_bar.visibility == View.INVISIBLE
        set(value) { progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE }

    private val adapter = SearchAdapter()

    lateinit var presenter: SearchContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_search, container, false)
        view.hits.layoutManager = LinearLayoutManager(context)
        view.hits.adapter = adapter
        presenter.takeView(this)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }

    override fun clear(shouldNotify: Boolean) {
        adapter.clear(shouldNotify)
    }

    override fun addHits(results: PeopleSearchResult) {
        adapter.addHits(results)
    }

    override fun showNoResults(show: Boolean) {
        no_result.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showEmptyState(show: Boolean) {

    }

    override fun showResults(show: Boolean) {

    }
}