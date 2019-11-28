package media.pixi.appkit.ui.chatcreator

import android.app.Activity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface ChatCreatorContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean
        var canCreate: Boolean

        fun setContacts(results: List<UserProfile>)
        fun setSelectedContacts(results: Set<UserProfile>)
        fun showNoResults(show: Boolean)
    }

    interface Presenter: BasePresenter<View> {
        fun onListItemClicked(activity: Activity, userProfile: UserProfile)
        fun onCreateChatClicked(activity: Activity)
        fun onRemoveContactClicked(activity: Activity, userProfile: UserProfile)
        fun onSelectedContactClicked(activity: Activity, userProfile: UserProfile)
        fun onTextChanged(query: String)
    }

    interface Navigator {
        fun showNewChat(activity: Activity, userIds: ArrayList<CharSequence>)
        fun showUserProfile(activity: Activity, userId: String)
    }
}