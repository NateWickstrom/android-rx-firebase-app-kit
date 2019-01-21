package media.pixi.rx.firebase.auth.kit.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject

class SignupFragment @Inject constructor(): DaggerFragment(), SignupContract.View {

    lateinit var presenter: SignupContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_signup, container, false)

        presenter.takeView(this)

        return root
    }
}