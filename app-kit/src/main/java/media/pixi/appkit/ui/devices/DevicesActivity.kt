package media.pixi.appkit.ui.devices

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject


class DevicesActivity : DaggerAppCompatActivity() {

    lateinit var fragment: DevicesFragment
        @Inject set

    lateinit var presenter: DevicesPresenter
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity)
        setSupportActionBar(toolbar)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )

        fragment.presenter = presenter
    }

}
