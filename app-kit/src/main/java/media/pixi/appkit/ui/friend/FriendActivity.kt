package media.pixi.appkit.ui.friend

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__activity_profile.*
import media.pixi.appkit.R
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject

class FriendActivity: DaggerAppCompatActivity() {

    lateinit var fragment: FriendFragment
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_profile)
        setSupportActionBar(toolbar)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }
}