package media.pixi.appkit.ui.profile

import android.app.Activity

interface ProfileContract {

    interface View {
        var profileImageUrl: String
        var profileTitle: String
        var profileSubtitle: String
        var friendCount: Int
    }

    interface Presenter {
        fun takeView(view: View)
        fun dropView()

        fun onFriendsClicked(activity: Activity)
    }

    interface Navigator {
        fun showFriendsScreen(activity: Activity)
    }
}