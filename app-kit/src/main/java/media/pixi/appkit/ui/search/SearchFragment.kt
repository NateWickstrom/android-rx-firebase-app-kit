package media.pixi.appkit.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_list.*
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R
import media.pixi.appkit.data.search.PeopleSearchResult
import javax.inject.Inject

class SearchFragment @Inject constructor(): DaggerFragment(), SearchContract.View {

    override var loading: Boolean
        get() = progress_bar.visibility == View.INVISIBLE
        set(value) { progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE }

    private var adapter: SearchAdapter? = null

    lateinit var presenter: SearchContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_list, container, false)
        view.list.layoutManager = LinearLayoutManager(context)

        adapter = SearchAdapter()
        adapter?.onClickListener = { presenter.onListItemClicked(activity as Activity, it) }
        view.list.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
        adapter = null
    }

    override fun clear(shouldNotify: Boolean) {
        adapter?.clear(shouldNotify)
    }

    override fun addHits(results: PeopleSearchResult) {
        adapter?.addHits(results)
    }

    override fun showNoResults(show: Boolean) {
        empty_message.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showEmptyState(show: Boolean) {

    }

    override fun showResults(show: Boolean) {

    }
}