package media.pixi.appkit.ui.profile

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__activity_profile.*
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject
import media.pixi.appkit.R
import media.pixi.appkit.utils.ImageUtils


class ProfileActivity : DaggerAppCompatActivity(), ProfileContract.View, AppBarLayout.OnOffsetChangedListener {

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
        set(value) { btn_friends.text = resources.getQuantityString(R.plurals.friends_count, value, value) }

    lateinit var fragment: ProfileFragment
        @Inject set

    lateinit var profilePresenter: ProfilePresenter
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_profile)
        setSupportActionBar(toolbar)
        title = ""

        appbar.addOnOffsetChangedListener(this)

        btn_friends.setOnClickListener { profilePresenter.onFriendsClicked(this) }

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )

        profilePresenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        profilePresenter.dropView()
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
        btn_friends.alpha = inverse

        collapsed_title.alpha = percentage
    }
}