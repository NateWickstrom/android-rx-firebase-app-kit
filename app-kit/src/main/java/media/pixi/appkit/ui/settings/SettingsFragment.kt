package media.pixi.appkit.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_settings.view.*
import media.pixi.appkit.R
import javax.inject.Inject

class SettingsFragment @Inject constructor(): DaggerFragment(), SettingsContract.View {

    lateinit var presenter: SettingsContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.appkit__fragment_settings, container, false)

        view.account.setOnClickListener {  }
        view.notifications.setOnClickListener {  }

        presenter.takeView(this)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }
}