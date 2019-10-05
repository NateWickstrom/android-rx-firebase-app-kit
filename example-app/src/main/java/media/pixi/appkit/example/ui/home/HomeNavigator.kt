package media.pixi.appkit.example.ui.home

import android.app.Activity
import android.content.Intent
import media.pixi.appkit.ui.devices.DevicesActivity
import media.pixi.appkit.ui.friends.FriendsActivity
import media.pixi.appkit.ui.notifications.NotificationsActivity
import media.pixi.appkit.ui.myprofile.ProfileActivity
import media.pixi.appkit.ui.search.SearchActivity
import media.pixi.appkit.ui.settings.SettingsActivity
import javax.inject.Inject

class HomeNavigator @Inject constructor(): HomeContract.Navigator {

    override fun showFriends(activity: Activity, forUserId: String) {
        FriendsActivity.launch(activity, forUserId)
    }

    override fun showDevices(activity: Activity) {
        val intent = Intent(activity, DevicesActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showNotification(activity: Activity) {
        val intent = Intent(activity, NotificationsActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showSettings(activity: Activity) {
        val intent = Intent(activity, SettingsActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showUserProfile(activity: Activity) {
        val intent = Intent(activity, ProfileActivity::class.java)
        activity.startActivity(intent)
    }

    override fun showSearch(activity: Activity) {
        val intent = Intent(activity, SearchActivity::class.java)
        activity.startActivity(intent)
    }
}