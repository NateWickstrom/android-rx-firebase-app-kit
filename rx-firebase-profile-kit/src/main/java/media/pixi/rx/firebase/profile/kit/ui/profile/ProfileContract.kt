package media.pixi.rx.firebase.profile.kit.ui.profile

import android.app.Activity

interface ProfileContract {

    interface View {

    }

    interface Presenter {
        fun takeView(view: View)
        fun dropView()
    }

    interface Navigator {
        fun showAccountScreen(activity: Activity)
    }
}