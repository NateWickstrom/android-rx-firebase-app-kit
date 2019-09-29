package media.pixi.appkit.ui.notifications

import android.app.Activity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.ui.friend.FriendActivity
import javax.inject.Inject

class NotificationsNavigator @Inject constructor(): NotificationsContract.Navigator {

    override fun showProfile(activity: Activity, userProfile: UserProfile) {
        FriendActivity.launch(activity, userProfile.id)
    }

}