package media.pixi.appkit.ui.chats

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject

class ChatsActivity : DaggerAppCompatActivity() {

    lateinit var fragment: ChatsFragment
        @Inject set
    lateinit var presenter: ChatsPresenter
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fragment.presenter = presenter

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ChatsActivity::class.java)
            activity.startActivity(intent)
        }
    }
}