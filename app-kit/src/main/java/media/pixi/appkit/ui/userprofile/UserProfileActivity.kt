package media.pixi.appkit.ui.userprofile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__activity_friend.*
import kotlinx.android.synthetic.main.appkit__activity_friend.progress_bar
import media.pixi.appkit.R
import media.pixi.appkit.utils.ActivityUtils
import media.pixi.appkit.utils.ImageUtils
import javax.inject.Inject

class UserProfileActivity: DaggerAppCompatActivity(), UserProfileContract.View, AppBarLayout.OnOffsetChangedListener {

    override var loading: Boolean
        get() = progress_bar.visibility == View.INVISIBLE
        set(value) { progress_bar.visibility = if (value) View.VISIBLE else View.INVISIBLE }

    override var error: String
        get() = ""
        set(value) {
            if (value.isNotBlank()) {
                Toast.makeText(this@UserProfileActivity, error, Toast.LENGTH_LONG).show()
            }
        }

    override var profileImageUrl: String
        get() = ""
        set(value) { ImageUtils.setUserImage(profile_image, value) }

    override var profileTitle: String
        get() = profile_title.text.toString()
        set(value) {
            profile_title.text = value
            collapsed_title.text = value
        }

    override var profileSubtitle: String
        get() = profile_subtitle.text.toString()
        set(value) { profile_subtitle.text = value}

    override var friendCount: Int
        get() = 0
        set(value) {
            btn_friends_count.visibility = View.VISIBLE
            btn_friends_count.text = resources.getQuantityString(R.plurals.friends_count, value, value)
        }

    override var isFriend: Boolean
        get() = true
        set(value) {
            if (value) {
                btn_add_friend.visibility = View.GONE
                btn_unfriend.visibility = View.VISIBLE
            } else {
                btn_add_friend.visibility = View.VISIBLE
                btn_unfriend.visibility = View.GONE
            }
        }

    lateinit var fragment: UserProfileFragment
        @Inject set

    lateinit var presenter: UserProfilePresenter
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_friend)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = ""

        presenter.userId = intent.extras?.getString(BUNDLE_USER)

        appbar.addOnOffsetChangedListener(this)

        btn_friends_count.setOnClickListener { presenter.onFriendsClicked(this) }
        btn_add_friend.setOnClickListener { presenter.onAddFriendClicked(this) }
        btn_unfriend.setOnClickListener { presenter.onUnFriendsClicked(this) }

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )

        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = Math.abs(offset).toFloat() / maxScroll.toFloat()

        val top = Math.min(Math.abs(offset * 2), maxScroll)
        val doublePercentage = top.toFloat() / maxScroll.toFloat()
        val inverse = 1 - doublePercentage

        profile_image.alpha = inverse
        profile_title.alpha = inverse
        profile_subtitle.alpha = inverse
        btn_friends_count.alpha = inverse

        collapsed_title.alpha = percentage
    }

    companion object {
        const val BUNDLE_USER = "user_id"

        fun launch(activity: Activity, userId: String) {
            val intent = Intent(activity, UserProfileActivity::class.java)
            intent.putExtra(BUNDLE_USER, userId)
            activity.startActivity(intent)
        }
    }
}