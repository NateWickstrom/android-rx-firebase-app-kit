package media.pixi.rx.firebase.auth.kit.ui.passwordupdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_password_update.view.*
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject

class PasswordUpdateFragment @Inject constructor(): DaggerFragment(), PasswordUpdateContract.View {

    lateinit var presenter: PasswordUpdateContract.Presenter
        @Inject set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_password_update, container, false)

        presenter.takeView(this)

        root.btn_update.setOnClickListener { presenter.onUpdateClicked(activity!!) }

        return root
    }
}