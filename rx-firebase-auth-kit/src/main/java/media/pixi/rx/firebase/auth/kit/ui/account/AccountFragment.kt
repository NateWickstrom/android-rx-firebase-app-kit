package media.pixi.rx.firebase.auth.kit.ui.account

import android.os.Bundle
import android.view.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.auth__error.view.*
import kotlinx.android.synthetic.main.auth__fragment_account.view.*
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject

class AccountFragment @Inject constructor(): DaggerFragment(), AccountContract.View {

    override var username: String
        get() = viewOfLayout.username.text.toString()
        set(value) { viewOfLayout.username.setText(value) }

    override var email: String
        get() = viewOfLayout.email.text.toString()
        set(value) { viewOfLayout.email.setText(value) }

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

    lateinit var presenter: AccountContract.Presenter
        @Inject set

    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.auth__fragment_account, container, false)

        presenter.takeView(this)

        viewOfLayout.btn_update_password.setOnClickListener { presenter.onUpdatePasswordClicked(activity!!) }
        viewOfLayout.btn_reset.setOnClickListener { presenter.onResetClicked(activity!!) }
        viewOfLayout.btn_save.setOnClickListener { presenter.onSaveClicked(activity!!) }
        viewOfLayout.btn_verify_email.setOnClickListener { presenter.onVerifyEmailClicked(activity!!) }

        return viewOfLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.auth__account_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sign_out -> {
                presenter.onSignOutClicked(activity!!)
                return true
            }
        }

        return false
    }
}