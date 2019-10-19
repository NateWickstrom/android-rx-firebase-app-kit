package media.pixi.appkit.example.ui.home

import android.app.Activity
import media.pixi.appkit.data.auth.AuthProvider
import javax.inject.Inject

class HomePresenter @Inject constructor(private val navigator: HomeNavigator,
                                        private val authProvider: AuthProvider):
    HomeContract.Presenter {

    override fun onFriendsClicked(activity: Activity) {
        navigator.showFriends(activity, authProvider.getUserId()!!)
    }

    override fun onChatClicked(activity: Activity) {
        navigator.showChat(activity)
    }

    override fun onDevicesClicked(activity: Activity) {
        navigator.showDevices(activity)
    }

    override fun onNotificationsClicked(activity: Activity) {
        navigator.showNotification(activity)
    }

    override fun onSettingsClicked(activity: Activity) {
        navigator.showSettings(activity)
    }

    override fun onProfileClicked(activity: Activity) {
        navigator.showUserProfile(activity)
    }

    override fun onSearchClicked(activity: Activity) {
        navigator.showSearch(activity)
    }
}