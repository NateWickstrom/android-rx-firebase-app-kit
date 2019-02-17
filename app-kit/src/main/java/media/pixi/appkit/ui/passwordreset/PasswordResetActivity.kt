package media.pixi.appkit.ui.passwordreset

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject

class PasswordResetActivity: DaggerAppCompatActivity() {

    lateinit var fragment: PasswordResetFragment
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val email = intent.extras?.getString(BUNDLE_EMAIL)
        fragment.message = getString(R.string.password_reset_complete_message, email)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    companion object {
        const val BUNDLE_EMAIL = "email_address"

        fun lauch(activity: Activity, email: String) {
            val intent = Intent(activity, PasswordResetActivity::class.java)
            intent.putExtra(BUNDLE_EMAIL, email)
            activity.startActivity(intent)
        }
    }
}