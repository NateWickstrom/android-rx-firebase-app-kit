package media.pixi.appkit.ui.notifications

import android.app.Activity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.ui.userprofile.UserProfileActivity
import javax.inject.Inject

class NotificationsNavigator @Inject constructor(): NotificationsContract.Navigator {

    override fun showProfile(activity: Activity, userProfile: UserProfile) {
        UserProfileActivity.launch(activity, userProfile.id)
    }

}