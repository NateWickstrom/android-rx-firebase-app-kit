package media.pixi.appkit.ui.chatmembers

import android.app.Activity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView


interface ChatMembersContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean

        fun setResults(results: List<UserProfile>)
        fun showNoResults(show: Boolean)
    }

    interface Presenter: BasePresenter<View> {
        var chatId: String?

        fun onListItemClicked(activity: Activity, userProfile: UserProfile)

    }

    interface Navigator {
        fun showUserProfileScreen(activity: Activity, userId: String)
        fun showMyProfilecreen(activity: Activity)

    }
}