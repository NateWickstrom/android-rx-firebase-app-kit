package media.pixi.appkit.ui.friends

import android.app.Activity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView


interface FriendsContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean

        fun setResults(results: List<UserProfile>)

    }

    interface Presenter: BasePresenter<View> {
        var userId: String?

        fun onListItemClicked(activity: Activity, userProfile: UserProfile)

    }

    interface Navigator {
        fun showFriendScreen(activity: Activity, userId: String)
    }
}