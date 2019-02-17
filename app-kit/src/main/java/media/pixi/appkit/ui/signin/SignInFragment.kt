package media.pixi.appkit.ui.signin

import android.os.Bundle
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import android.view.*
import kotlinx.android.synthetic.main.appkit__error.view.*
import kotlinx.android.synthetic.main.appkit__fragment_signin.*
import kotlinx.android.synthetic.main.appkit__fragment_signin.view.*
import media.pixi.appkit.R
import media.pixi.appkit.ui.TextChangeWatcher


class SignInFragment @Inject constructor(): DaggerFragment(), SignInContract.View {

    override var loading: Boolean
        get() = progress_bar.visibility == View.INVISIBLE
        set(value) { progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE }

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

    lateinit var presenter: SignInContract.Presenter
        @Inject set

    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.appkit__fragment_signin, container, false)

        presenter.takeView(this)

        viewOfLayout.email.addTextChangedListener(TextChangeWatcher { presenter.onEmailTextChanged(it) })
        viewOfLayout.password.addTextChangedListener(TextChangeWatcher { presenter.onPasswordTextChanged(it) })
        viewOfLayout.sign_in.setOnClickListener { presenter.onSignInClicked(activity!!) }
        viewOfLayout.forgot_password.setOnClickListener { presenter.onForgotPasswordClicked(activity!!) }

        return viewOfLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appkit__signin_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_signin -> {
                presenter.onSignUpClicked(activity!!)
                return true
            }
        }

        return false
    }
}