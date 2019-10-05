package media.pixi.appkit.ui.userprofile

import android.app.Activity
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface UserProfileContract {

    interface View: BaseView<Presenter> {
        var profileImageUrl: String
        var profileTitle: String
        var profileSubtitle: String
        var friendCount: Int
        var isFriend: Boolean
        var loading: Boolean
    }

    interface Presenter: BasePresenter<View> {
        var userId: String?

        fun onFriendsClicked(activity: Activity)
        fun onUnFriendsClicked(activity: Activity)
        fun onAddFriendClicked(activity: Activity)

    }

    interface Navigator {
        fun showFriendsScreen(activity: Activity, forUserId: String)
    }
}