package media.pixi.rx.firebase.auth.kit.ui.signup

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.auth__appbar.*
import media.pixi.common.ActivityUtils
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject

class SignUpActivity : DaggerAppCompatActivity() {

    lateinit var fragment: SignUpFragment
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth__activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

}