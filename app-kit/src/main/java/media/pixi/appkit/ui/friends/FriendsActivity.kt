package media.pixi.appkit.ui.friends

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R
import javax.inject.Inject


class FriendsActivity: DaggerAppCompatActivity(), FriendsContract.View {

    lateinit var presenter: FriendsPresenter
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userId = intent.extras?.getString(BUNDLE_USER)

    }

    companion object {
        private const val BUNDLE_USER = "user_id"

        fun launch(activity: Activity, forUserId: String) {
            val intent = Intent(activity, FriendsActivity::class.java)
            intent.putExtra(BUNDLE_USER, forUserId)
            activity.startActivity(intent)
        }
    }
}