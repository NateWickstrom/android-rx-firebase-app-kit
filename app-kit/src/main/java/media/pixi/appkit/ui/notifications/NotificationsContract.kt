package media.pixi.appkit.ui.notifications

import android.app.Activity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.domain.notifications.Notification
import media.pixi.appkit.ui.BasePresenter
import media.pixi.appkit.ui.BaseView

interface NotificationsContract {

    interface View: BaseView<Presenter> {
        var loading: Boolean
        var hasResults: Boolean

        fun setResults(results: List<Notification>)
        fun set(position: Int, notification: Notification)

        fun showMessage(message: String, onUndoClickListener: () -> Unit)
    }

    interface Presenter: BasePresenter<View> {

        fun onItemClicked(activity: Activity, notification: Notification, position: Int)
        fun onItemLongClicked(activity: Activity, notification: Notification, position: Int)
        fun onAcceptFriendRequestClicked(notification: Notification, position: Int)
        fun onDeleteNotification(position: Int)

    }

    interface Navigator {
        fun showProfile(activity: Activity, userProfile: UserProfile)
    }
}