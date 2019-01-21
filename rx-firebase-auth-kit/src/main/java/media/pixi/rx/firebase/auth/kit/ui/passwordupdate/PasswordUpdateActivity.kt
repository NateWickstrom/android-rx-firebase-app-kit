package media.pixi.rx.firebase.auth.kit.ui.passwordupdate

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appbar.*
import media.pixi.rx.firebase.auth.kit.R
import media.pixi.rx.firebase.auth.kit.ui.ActivityUtils
import javax.inject.Inject

class PasswordUpdateActivity : DaggerAppCompatActivity() {

    lateinit var fragment: PasswordUpdateFragment
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

}