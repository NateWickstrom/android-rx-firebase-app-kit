package media.pixi.rx.firebase.auth.kit.example.ui.splash

import android.app.Activity
import android.content.Intent

interface SplashContract {

    interface View {

    }

    interface Presenter {
        fun onLaunch(activity: Activity)
        fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?)
    }

    interface Navigator {
        fun showLoginScreen(activity: Activity)
        fun showHomeScreen(activity: Activity)
        fun exit(activity: Activity)

    }

    companion object {
        const val REQUEST_CODE = 100
    }
}