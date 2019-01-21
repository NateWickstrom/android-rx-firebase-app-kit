package media.pixi.rx.firebase.auth.kit.ui.account

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appbar.*
import media.pixi.rx.firebase.auth.kit.R
import media.pixi.rx.firebase.auth.kit.ui.ActivityUtils
import javax.inject.Inject

class AccountActivity : DaggerAppCompatActivity() {

    lateinit var fragment: AccountFragment
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
