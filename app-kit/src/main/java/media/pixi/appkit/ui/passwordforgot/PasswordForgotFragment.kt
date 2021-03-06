package media.pixi.appkit.ui.passwordforgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__error.view.*
import kotlinx.android.synthetic.main.appkit__fragment_password_forgot.view.*
import kotlinx.android.synthetic.main.appkit__loading.view.*
import media.pixi.appkit.R
import javax.inject.Inject

class PasswordForgotFragment @Inject constructor(): DaggerFragment(), PasswordForgotContract.View {

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

    override var email: String
        get() = viewOfLayout.email.text.toString()
        set(value) { viewOfLayout.email.setText(value) }


    override var loading: Boolean
        get() = viewOfLayout.progress_bar.visibility == View.INVISIBLE
        set(value) {
            viewOfLayout.btn_send.isEnabled = !value
            viewOfLayout.progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    lateinit var presenter: PasswordForgotContract.Presenter
        @Inject set

    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.appkit__fragment_password_forgot, container, false)

        presenter.takeView(this)

        viewOfLayout.btn_send.setOnClickListener { activity?.let { presenter.onSendClicked(it) } }

        return viewOfLayout
    }
}