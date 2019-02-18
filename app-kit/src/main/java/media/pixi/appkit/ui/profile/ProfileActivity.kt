package media.pixi.appkit.ui.profile

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__activity_profile.*
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject
import io.reactivex.disposables.Disposable
import media.pixi.appkit.R
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.auth.AuthUserModel
import media.pixi.appkit.utils.ImageUtils
import timber.log.Timber


class ProfileActivity : DaggerAppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    lateinit var fragment: ProfileFragment
        @Inject set

    lateinit var userProfileProvider: AuthProvider
        @Inject set

    lateinit var navigator: ProfileContract.Navigator
        @Inject set

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_profile)
        setSupportActionBar(toolbar)
        title = ""

        appbar.addOnOffsetChangedListener(this)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )

        disposable?.dispose()
        disposable = userProfileProvider.observerLoggedInUser()
            .subscribe(
                { updateUser(it) },
                { Timber.e(it.message, it) }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        disposable = null
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

    private fun updateUser(user: AuthUserModel) {
        ImageUtils.setUserImage(profile_image, user.imageUrl)

        val name = "${user.firstName} ${user.lastName}"

        profile_title.text = name
        profile_subtitle.text = user.username
        collapsed_title.text = name
    }
}