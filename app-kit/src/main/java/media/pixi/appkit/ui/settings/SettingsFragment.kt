package media.pixi.appkit.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_search.view.*
import media.pixi.appkit.R
import javax.inject.Inject

class SettingsFragment @Inject constructor(): DaggerFragment(), SettingsContract.View {

    private val adapter = SettingsAdaptor()

    lateinit var presenter: SettingsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_settings, container, false)
        view.hits.layoutManager = LinearLayoutManager(context)
        view.hits.adapter = adapter
        presenter.takeView(this)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }
}