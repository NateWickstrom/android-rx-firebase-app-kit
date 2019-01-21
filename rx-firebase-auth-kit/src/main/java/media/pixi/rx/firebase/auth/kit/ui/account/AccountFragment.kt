package media.pixi.rx.firebase.auth.kit.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.error.view.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject

class AccountFragment @Inject constructor(): DaggerFragment(), AccountContract.View {

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
        viewOfLayout = inflater.inflate(R.layout.fragment_account, container, false)

        presenter.takeView(this)

        viewOfLayout.btn_update_password.setOnClickListener { presenter.onUpdatePasswordClicked(activity!!) }
        viewOfLayout.btn_reset.setOnClickListener { presenter.onResetClicked(activity!!) }
        viewOfLayout.btn_save.setOnClickListener { presenter.onSaveClicked(activity!!) }
        viewOfLayout.btn_verify_email.setOnClickListener { presenter.onVerifyEmailClicked(activity!!) }

        return viewOfLayout
    }

    private fun updateError(error: String) {
        if (error.isNotBlank()) {
            viewOfLayout.error.visibility = View.INVISIBLE
        } else {
            viewOfLayout.error.visibility = View.VISIBLE
            viewOfLayout.error_massage.text = error
        }
    }
}