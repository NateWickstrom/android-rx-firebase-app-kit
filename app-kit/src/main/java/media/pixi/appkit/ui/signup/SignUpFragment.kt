package media.pixi.appkit.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__error.view.*
import kotlinx.android.synthetic.main.appkit__fragment_signup.view.*
import media.pixi.appkit.R
import media.pixi.appkit.ui.TextChangeWatcher
import javax.inject.Inject

class SignUpFragment @Inject constructor(): DaggerFragment(), SignUpContract.View {

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

    lateinit var presenter: SignUpContract.Presenter
        @Inject set

    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.appkit__fragment_signup, container, false)

        presenter.takeView(this)

        viewOfLayout.email.addTextChangedListener(TextChangeWatcher { presenter.onEmailTextChanged(it) })
        viewOfLayout.password.addTextChangedListener(TextChangeWatcher { presenter.onPasswordTextChanged(it) })
        viewOfLayout.sign_up.setOnClickListener { presenter.onSignUpClicked(activity!!) }

        return viewOfLayout
    }
}