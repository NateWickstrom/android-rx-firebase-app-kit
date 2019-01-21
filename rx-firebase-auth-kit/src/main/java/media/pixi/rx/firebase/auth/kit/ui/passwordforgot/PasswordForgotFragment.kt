package media.pixi.rx.firebase.auth.kit.ui.passwordforgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_password_forgot.view.*
import kotlinx.android.synthetic.main.fragment_signin.view.*
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject

class PasswordForgotFragment @Inject constructor(): DaggerFragment(), PasswordForgotContract.View {

    lateinit var presenter: PasswordForgotContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_password_forgot, container, false)

        presenter.takeView(this)

        root.btn_send.setOnClickListener { presenter.onSendClicked(activity!!) }

        return root
    }
}