package media.pixi.appkit.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.algolia.instantsearch.helper.android.list.autoScrollToStart
import kotlinx.android.synthetic.main.appkit__fragment_list.view.*
import media.pixi.appkit.R

class SearchFragment : Fragment() {

    lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.appkit__fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.products.observe(this as LifecycleOwner, Observer { hits -> viewModel.adapterProduct.submitList(hits) })

        view.list.let {
            it.itemAnimator = null
            it.adapter = viewModel.adapterProduct
            it.layoutManager = LinearLayoutManager(requireContext())
            it.autoScrollToStart(viewModel.adapterProduct)
        }
    }
}