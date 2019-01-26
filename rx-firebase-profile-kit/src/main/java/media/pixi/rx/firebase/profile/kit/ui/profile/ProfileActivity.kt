package media.pixi.rx.firebase.profile.kit.ui.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.profile__activity.*
import media.pixi.common.ActivityUtils
import media.pixi.rx.firebase.profile.kit.R
import javax.inject.Inject
import android.view.animation.AlphaAnimation
import android.view.View
import kotlinx.android.synthetic.main.profile__activity.view.*
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import com.google.android.material.snackbar.Snackbar



class ProfileActivity : DaggerAppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    lateinit var fragment: ProfileFragment
        @Inject set

    lateinit var authProvider: AuthProvider
        @Inject set

    lateinit var navigator: ProfileContract.Navigator
        @Inject set

    private var mIsTheTitleVisible = false
    private var mIsTheTitleContainerVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile__activity)
        setSupportActionBar(toolbar)

        title = ""
        appbar.addOnOffsetChangedListener(this)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )

        val user = authProvider.getUser()
        if (user != null) {
            avatar.setImageURI(user.imageUrl)
            profile_title.text = user.username
            collapsed_title.text = user.username
        }

        fab.setOnClickListener({ view ->
            navigator.showAccountScreen(this)
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.profile__menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            media.pixi.rx.firebase.auth.kit.R.id.menu_signin -> {
//                presenter.onSignUpClicked(activity!!)
//                return true
//            }
//        }
//
//        return false
//    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = Math.abs(offset).toFloat() / maxScroll.toFloat()

        handleAlphaOnTitle(percentage)
        handleToolbarTitleVisibility(percentage)
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(collapsed_title, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                mIsTheTitleVisible = true
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(collapsed_title, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                mIsTheTitleVisible = false
            }
        }
    }

    private fun handleAlphaOnTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayout_title, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                mIsTheTitleContainerVisible = false
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayout_title, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                mIsTheTitleContainerVisible = true
            }
        }
    }

    fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
        val alphaAnimation = if (visibility == View.VISIBLE)
            AlphaAnimation(0f, 1f)
        else
            AlphaAnimation(1f, 0f)

        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }

    companion object {

        private const val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f
        private const val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
        private const val ALPHA_ANIMATIONS_DURATION = 200L
    }
}