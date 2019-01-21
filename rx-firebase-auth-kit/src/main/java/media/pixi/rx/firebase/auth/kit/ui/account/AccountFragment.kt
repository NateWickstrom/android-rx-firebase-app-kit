package media.pixi.rx.firebase.auth.kit.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_account.view.*
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject

class AccountFragment @Inject constructor(): DaggerFragment(), AccountContract.View {

    lateinit var presenter: AccountContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_account, container, false)

        presenter.takeView(this)

        root.btn_update_password.setOnClickListener { presenter.onUpdatePasswordClicked(activity!!) }
        root.btn_reset.setOnClickListener { presenter.onResetClicked(activity!!) }
        root.btn_save.setOnClickListener { presenter.onSaveClicked(activity!!) }
        root.btn_verify_email.setOnClickListener { presenter.onVerifyEmailClicked(activity!!) }

        return root
    }
}