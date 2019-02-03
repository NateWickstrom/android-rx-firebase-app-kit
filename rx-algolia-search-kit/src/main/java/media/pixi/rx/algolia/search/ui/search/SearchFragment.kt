package media.pixi.rx.algolia.search.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import media.pixi.rx.algolia.search.R
import javax.inject.Inject

class SearchFragment @Inject constructor(): DaggerFragment(), SearchContract.View {

    lateinit var presenter: SearchContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.search__fragment_search, container, false)
        presenter.takeView(this)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }
}