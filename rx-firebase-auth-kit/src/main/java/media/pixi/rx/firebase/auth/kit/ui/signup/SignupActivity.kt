package media.pixi.rx.firebase.auth.kit.ui.signup

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appbar.*
import media.pixi.rx.firebase.auth.kit.R
import media.pixi.rx.firebase.auth.kit.ui.ActivityUtils
import javax.inject.Inject

class SignupActivity : DaggerAppCompatActivity() {

    lateinit var fragment: SignupFragment
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        setSupportActionBar(toolbar)
        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

}