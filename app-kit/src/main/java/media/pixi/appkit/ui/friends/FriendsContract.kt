package media.pixi.appkit.ui.friends

import android.app.Activity


interface FriendsContract {

    interface View {

    }

    interface Presenter {
        fun takeView(view: FriendsContract.View)
        fun dropView()

        fun onFriendsClicked(activity: Activity)
    }

    interface Navigator {
        fun showFriendScreen(activity: Activity, userId: String)
    }
}