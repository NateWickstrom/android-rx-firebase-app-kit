package media.pixi.appkit.ui.account

import android.os.Bundle
import android.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.appkit__error.view.*
import kotlinx.android.synthetic.main.appkit__fragment_account.*
import kotlinx.android.synthetic.main.appkit__fragment_account.view.*
import media.pixi.appkit.R
import media.pixi.appkit.utils.ImageUtils
import java.io.File
import javax.inject.Inject

class AccountFragment @Inject constructor(): DaggerFragment(), AccountContract.View {

    override var userImageUrl: String
        get() = ""
        set(value) {
            if (value.isNotEmpty()) {
                ImageUtils.setUserImage(user_image, value)
            }
        }

    override var username: String
        get() = viewOfLayout.username.text.toString()
        set(value) { viewOfLayout.username.setText(value) }

    override var firstName: String
        get() = viewOfLayout.firstname.text.toString()
        set(value) { viewOfLayout.firstname.setText(value) }

    override var lastName: String
        get() = viewOfLayout.lastname.text.toString()
        set(value) { viewOfLayout.lastname.setText(value) }

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
        viewOfLayout = inflater.inflate(R.layout.appkit__fragment_account, container, false)
        viewOfLayout.btn_update_password.setOnClickListener { presenter.onUpdatePasswordClicked(activity!!) }
        viewOfLayout.btn_reset.setOnClickListener { presenter.onResetClicked(activity!!) }
        viewOfLayout.btn_save.setOnClickListener { presenter.onSaveClicked(activity!!) }
        viewOfLayout.btn_verify_email.setOnClickListener { presenter.onVerifyEmailClicked(activity!!) }
        viewOfLayout.user_image.setOnClickListener { presenter.onUserImageClicked(activity!!) }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appkit__account_menu, menu)
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

    fun setImage(file: File) {
        Glide.with(context!!)
            .load(file)
            .apply(RequestOptions.circleCropTransform())
            .into(user_image)
    }
}