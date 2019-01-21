package media.pixi.rx.firebase.auth.kit.ui.passwordupdate

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_signin.*
import media.pixi.rx.firebase.auth.kit.R
import media.pixi.rx.firebase.auth.kit.ui.ActivityUtils
import javax.inject.Inject

class PasswordUpdateActivity : DaggerAppCompatActivity() {

    var passwordUpdateFragment: PasswordUpdateFragment? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setSupportActionBar(toolbar)

        val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)

        if (fragment == null) {
            // Get the fragment from the subclass
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager, passwordUpdateFragment!!, R.id.contentFrame
            )
        }
    }

}