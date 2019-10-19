package media.pixi.appkit.example.ui.home

import android.app.Activity

interface HomeContract {

    interface View {

    }

    interface Presenter {
        fun onProfileClicked(activity: Activity)
        fun onChatClicked(activity: Activity)
        fun onSearchClicked(activity: Activity)
        fun onSettingsClicked(activity: Activity)
        fun onNotificationsClicked(activity: Activity)
        fun onDevicesClicked(activity: Activity)
        fun onFriendsClicked(activity: Activity)
    }

    interface Navigator {
        fun showUserProfile(activity: Activity)
        fun showChat(activity: Activity)
        fun showSearch(activity: Activity)
        fun showSettings(activity: Activity)
        fun showNotification(activity: Activity)
        fun showDevices(activity: Activity)
        fun showFriends(activity: Activity, forUserId: String)
    }
}