package media.pixi.appkit.ui.friend

import android.app.Activity

interface FriendContract {

    interface View {
        var profileImageUrl: String
        var profileTitle: String
        var profileSubtitle: String
        var friendCount: Int
        var isFriend: Boolean
    }

    interface Presenter {
        var userId: String?

        fun takeView(view: View)
        fun dropView()

        fun onFriendsClicked(activity: Activity)
        fun onUnFriendsClicked(activity: Activity)
        fun onAddFriendClicked(activity: Activity)

    }

    interface Navigator {
        fun showFriendsScreen(activity: Activity)
    }
}