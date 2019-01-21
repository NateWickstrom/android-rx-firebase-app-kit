package media.pixi.rx.firebase.auth.kit.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_signup.view.*
import media.pixi.rx.firebase.auth.kit.R
import media.pixi.rx.firebase.auth.kit.ui.TextChangeWatcher
import javax.inject.Inject

class SignUpFragment @Inject constructor(): DaggerFragment(), SignUpContract.View {

    lateinit var presenter: SignUpContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_signup, container, false)

        presenter.takeView(this)

        root.email.addTextChangedListener(TextChangeWatcher { presenter.onEmailTextChanged(it) })
        root.password.addTextChangedListener(TextChangeWatcher { presenter.onPasswordTextChanged(it) })
        root.sign_up.setOnClickListener { presenter.onSignUpClicked(activity!!) }

        return root
    }
}