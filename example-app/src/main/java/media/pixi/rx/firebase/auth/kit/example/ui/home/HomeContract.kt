package media.pixi.rx.firebase.auth.kit.example.ui.home

import android.app.Activity

interface HomeContract {

    interface View {

    }

    interface Presenter {
        fun onProfileClicked(activity: Activity)
    }

    interface Navigator {
        fun showUserProfile(activity: Activity)
    }
}