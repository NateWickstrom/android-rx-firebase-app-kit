package media.pixi.rx.firebase.profile.kit.ui.profile

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appbar.*
import media.pixi.common.ActivityUtils
import media.pixi.rx.firebase.profile.kit.R
import javax.inject.Inject

class ProfileActivity : DaggerAppCompatActivity() {

    lateinit var fragment: ProfileFragment
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