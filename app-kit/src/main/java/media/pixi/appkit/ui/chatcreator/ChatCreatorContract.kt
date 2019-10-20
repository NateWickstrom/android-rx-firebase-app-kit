package media.pixi.appkit.ui.chatcreator

import android.app.Activity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatCreatorContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean
        var canCreate: Boolean

        fun setResults(results: List<UserProfile>)

    }

    interface Presenter: BasePresenter<View> {
        fun onListItemClicked(activity: Activity, userProfile: UserProfile)
        fun onStartChatClicked(activity: Activity)
    }

    interface Navigator {

    }
}