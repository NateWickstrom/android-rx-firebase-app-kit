package media.pixi.rx.firebase.auth.kit.ui.signin

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.auth__appbar.*
import media.pixi.common.ActivityUtils
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject


class SignInActivity : DaggerAppCompatActivity() {

    lateinit var fragment: SignInFragment
        @Inject set
    lateinit var navigator: SignInNavigator
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth__activity)
        setSupportActionBar(toolbar)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        navigator.onActivityResult(this, requestCode, resultCode, data)
    }

}
