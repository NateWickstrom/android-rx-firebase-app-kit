package media.pixi.appkit.ui.passwordreset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__fragment_password_reset.view.*
import media.pixi.appkit.R
import javax.inject.Inject

class PasswordResetFragment @Inject constructor(): DaggerFragment(), PasswordResetContract.View {

    override var message: String? = null

    lateinit var presenter: PasswordResetContract.Presenter
        @Inject set

    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.appkit__fragment_password_reset, container, false)

        presenter.takeView(this)

        viewOfLayout.btn_email.setOnClickListener { presenter.onOpenEmailClicked(activity!!) }
        viewOfLayout.message.text = message

        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }
}
