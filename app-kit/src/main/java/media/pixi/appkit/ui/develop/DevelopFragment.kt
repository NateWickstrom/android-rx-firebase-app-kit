package media.pixi.appkit.ui.develop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__error.view.*
import kotlinx.android.synthetic.main.appkit__fragment_develop.view.*
import media.pixi.appkit.R
import javax.inject.Inject

class DevelopFragment @Inject constructor(): DaggerFragment(), DevelopContract.View {

    override var error: String
        get() = viewOfLayout.error_massage.text.toString()
        set(value) {
            if (value.isNotBlank()) {
                viewOfLayout.error.visibility = View.VISIBLE
                viewOfLayout.error_massage.text = value
            } else {
                viewOfLayout.error.visibility = View.INVISIBLE
            }
        }

    override var userId: String
        get() = ""
        set(value) { viewOfLayout.user_id.text = value }

    override var deviceId: String
        get() = ""
        set(value) { viewOfLayout.device_id.text = value }

    lateinit var presenter: DevelopContract.Presenter
        @Inject set

    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.appkit__fragment_develop, container, false)
        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }

}