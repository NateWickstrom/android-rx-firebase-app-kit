package media.pixi.rx.algolia.search.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.search__fragment_search.view.*
import media.pixi.rx.algolia.search.R
import media.pixi.rx.algolia.search.data.PeopleSearchResult
import javax.inject.Inject

class SearchFragment @Inject constructor(): DaggerFragment(), SearchContract.View {

    private val adapter = SearchAdapter()

    lateinit var presenter: SearchContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.search__fragment_search, container, false)
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

}