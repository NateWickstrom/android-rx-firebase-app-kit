package media.pixi.appkit.ui.friends

import android.app.Activity
import media.pixi.appkit.ui.userprofile.UserProfileActivity
import javax.inject.Inject

class FriendsNavigator @Inject constructor(): FriendsContract.Navigator {

    override fun showFriendScreen(activity: Activity, userId: String) {
        UserProfileActivity.launch(activity, userId)
    }

}
