package media.pixi.rx.firebase.auth.kit.ui.passwordupdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.error.view.*
import kotlinx.android.synthetic.main.fragment_password_update.view.*
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject

class PasswordUpdateFragment @Inject constructor(): DaggerFragment(), PasswordUpdateContract.View {

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

    lateinit var presenter: PasswordUpdateContract.Presenter
        @Inject set

    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_password_update, container, false)

        presenter.takeView(this)

        viewOfLayout.btn_update.setOnClickListener { presenter.onUpdateClicked(activity!!) }

        return viewOfLayout
    }
}