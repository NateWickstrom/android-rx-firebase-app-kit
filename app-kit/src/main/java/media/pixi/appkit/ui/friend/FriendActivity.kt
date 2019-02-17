package media.pixi.appkit.ui.friend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__activity_profile.*
import media.pixi.appkit.R
import media.pixi.appkit.ui.passwordreset.PasswordResetActivity
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject

class FriendActivity: DaggerAppCompatActivity(), FriendContract.View {

    override var userImageUrl: String
        get() = ""
        set(value) { avatar.setImageURI(value) }

    override var username: String
        get() = ""
        set(value) {
            profile_title.text = value
            collapsed_title.text = value
        }

    override var firstName: String
        get() = ""
        set(value) {}

    override var lastName: String
        get() = ""
        set(value) {}

    lateinit var fragment: FriendFragment
        @Inject set

    lateinit var presenter: FriendPresenter
        @Inject set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_friend)
        setSupportActionBar(toolbar)
        title = ""

        presenter.userId = intent.extras?.getString(BUNDLE_USER)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )

        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    companion object {
        const val BUNDLE_USER = "user_id"

        fun launch(activity: Activity, userId: String) {
            val intent = Intent(activity, FriendActivity::class.java)
            intent.putExtra(BUNDLE_USER, userId)
            activity.startActivity(intent)
        }
    }
}