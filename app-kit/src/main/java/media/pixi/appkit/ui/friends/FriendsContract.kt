package media.pixi.appkit.ui.friends

import android.app.Activity
import media.pixi.appkit.data.profile.UserProfile


interface FriendsContract {

    interface View {
        var loading: Boolean

        fun setResults(results: List<UserProfile>)

    }

    interface Presenter {
        var userId: String?

        fun takeView(view: FriendsContract.View)
        fun dropView()

        fun onListItemClicked(activity: Activity, userProfile: UserProfile)

    }

    interface Navigator {
        fun showFriendScreen(activity: Activity, userId: String)
    }
}