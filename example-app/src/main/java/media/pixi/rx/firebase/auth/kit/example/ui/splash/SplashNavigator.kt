package media.pixi.rx.firebase.auth.kit.example.ui.splash

import android.app.Activity
import android.content.Intent
import media.pixi.rx.firebase.auth.kit.example.ui.home.HomeActivity
import media.pixi.rx.firebase.auth.kit.example.ui.splash.SplashContract.Companion.REQUEST_CODE
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashNavigator @Inject constructor(): SplashContract.Navigator {

    override fun showLoginScreen(activity: Activity) {
        val intent = Intent(activity, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        activity.startActivityForResult(intent, REQUEST_CODE)
    }

    override fun showHomeScreen(activity: Activity) {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        activity.startActivity(intent)
        activity.finish()
    }

    override fun exit(activity: Activity) {
        activity.finish()
    }
}