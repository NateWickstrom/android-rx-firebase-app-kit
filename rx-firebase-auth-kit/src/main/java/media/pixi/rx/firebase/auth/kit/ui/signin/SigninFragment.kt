package media.pixi.rx.firebase.auth.kit.ui.signin

import android.os.Bundle
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import android.view.*
import kotlinx.android.synthetic.main.fragment_signin.view.*
import media.pixi.rx.firebase.auth.kit.R


class SigninFragment @Inject constructor(): DaggerFragment(), SigninContract.View {

    lateinit var presenter: SigninContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_signin, container, false)

        presenter.takeView(this)

        root.btn_forgot_password.setOnClickListener { presenter.onForgotPasswordClicked(activity!!) }

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.signin_menu, menu)
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