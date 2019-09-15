package media.pixi.appkit.ui.profile

import android.app.Activity

interface ProfileContract {

    interface View {
        var profileImageUrl: String
        var profileTitle: String
        var profileSubtitle: String
        var friendCount: Int
        var followerCount: Int

    }

    interface Presenter {
        fun takeView(view: View)
        fun dropView()

        fun onFriendsClicked(activity: Activity)

        fun onFollowersClicked(activity: Activity)
    }

    interface Navigator {
        fun showFriendsScreen(activity: Activity, forUserId: String)

        fun showFollowersScreen(activity: Activity, forUserId: String)
    }
}